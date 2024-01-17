package bus.project.oauth_resourceserver;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
@WebFluxTest(OAuthResourceServerController.class)
@Import(OAuthResourceServerSecurityConfig.class)
@ExtendWith(MockitoExtension.class)

class OAuthResourceServerApplicationTests {
    Consumer<HttpHeaders> noScopesToken = (http) -> http.setBearerAuth(
            "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjo0NjgzODA1MTI4fQ.ULEPdHG-MK5GlrTQMhgqcyug2brTIZaJIrahUeq9zaiwUSdW83fJ7W1IDd2Z3n4a25JY2uhEcoV95lMfccHR6y_2DLrNvfta22SumY9PEDF2pido54LXG6edIGgarnUbJdR4rpRe_5oRGVa8gDx8FnuZsNv6StSZHAzw5OsuevSTJ1UbJm4UfX3wiahFOQ2OI6G-r5TB2rQNdiPHuNyzG5yznUqRIZ7-GCoMqHMaC-1epKxiX8gYXRROuUYTtcMNa86wh7OVDmvwVmFioRcR58UWBRoO1XQexTtOQq_t8KYsrPZhb9gkyW8x2bAQF-d0J0EJY8JslaH6n4RBaZISww");

    Consumer<HttpHeaders> messageReadToken = (http) -> http.setBearerAuth(
            "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzdWJqZWN0Iiwic2NvcGUiOiJtZXNzYWdlOnJlYWQiLCJleHAiOjQ2ODM4MDUxNDF9.h-j6FKRFdnTdmAueTZCdep45e6DPwqM68ZQ8doIJ1exi9YxAlbWzOwId6Bd0L5YmCmp63gGQgsBUBLzwnZQ8kLUgUOBEC3UzSWGRqMskCY9_k9pX0iomX6IfF3N0PaYs0WPC4hO1s8wfZQ-6hKQ4KigFi13G9LMLdH58PRMK0pKEvs3gCbHJuEPw-K5ORlpdnleUTQIwINafU57cmK3KocTeknPAM_L716sCuSYGvDl6xUTXO7oPdrXhS_EhxLP6KxrpI1uD4Ea_5OWTh7S0Wx5LLDfU6wBG1DowN20d374zepOIEkR-Jnmr_QlR44vmRqS5ncrF-1R0EGcPX49U6A");

    @Autowired
    WebTestClient rest;

    @MockBean
    ReactiveJwtDecoder jwtDecoder;

    @Test
    void indexGreetsAuthenticatedUser() {
        // @formatter:off
        this.rest.mutateWith(mockJwt().jwt((jwt) -> jwt.subject("NICKNAME123")))
                .get()
                .uri("/")
                .exchange()
                .expectBody(String.class).isEqualTo("Hello, NICKNAME123!")
                .consumeWith(response -> {
                    String responseBody = new String(response.getResponseBodyContent());
                    System.out.println("Response Body: " + responseBody);
                });
        // @formatter:on
    }


    @Test
    void testGetSecuredData() {
        this.rest.mutateWith(mockJwt().jwt((jwt)-> jwt()))
                .get()
                .uri("/secured-data")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(String.class).isEqualTo("Secured Data for:")
                .consumeWith(response -> {
                    String responseBody = new String(response.getResponseBodyContent());
                    System.out.println("Response Body: " + responseBody);
                });
    }

    @Test
    void messageCanNotBeCreatedWithScopeMessageReadAuthority() {
        Jwt jwt = jwt().claim("scope", "message:read").build();
        given(this.jwtDecoder.decode(anyString())).willReturn(Mono.just(jwt));
        // @formatter:off
        this.rest.post()
                .uri("/message")
                .headers((headers) -> headers.setBearerAuth(jwt.getTokenValue()))
                .bodyValue("Hello message")
                .exchange()
                .expectStatus().isForbidden();
        // @formatter:on
    }

    @Test
    void GetsecuredDataWithNoAuthorization() {

        this.rest.get()
                .uri("/secured-data")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void GetsecuredData2() {
        Jwt jwt = jwt().claim("scope", "SCOPE_message:read").build();
        given(this.jwtDecoder.decode(anyString())).willReturn(Mono.just(jwt));
        this.rest
                .get()
                .uri("/secured-data")
                .headers((headers) -> headers.setBearerAuth(jwt.getTokenValue()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = new String(response.getResponseBodyContent());
                    System.out.println("Response Body: " + responseBody);
                });
        System.out.println("Decoded JWT Claims: " + jwtDecoder.decode("your-token-value").block().getClaims());

    }
    private Jwt.Builder jwt() {
        return Jwt.withTokenValue("valid_token_value")
                .header("alg", "SH256")
                .claim("scope", "SCOPE_message:read");

    }


}


