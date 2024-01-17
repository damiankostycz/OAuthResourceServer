package bus.project.oauth_resourceserver;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@WebFluxTest(OAuthResourceServerController.class)
@Import(OAuthResourceServerSecurityConfig.class)
@ExtendWith(MockitoExtension.class)

class OAuthResourceServerApplicationTests {

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
    void testGetSecuredDataWithBadScope() {
        this.rest.mutateWith(mockJwt().jwt((jwt) -> jwt.claim("scope", "message:fsadas")))
                .get()
                .uri("/all-clients")
                .exchange()
                .expectStatus().isForbidden();
    }


    @Test
    void GetSecuredDataWithNoAuthorization() {
        this.rest.get()
                .uri("/all-clients")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void GetSecuredDataWithGoodScope() {
        this.rest.mutateWith(mockJwt().jwt((jwt) -> jwt.claim("scope", "message:readAll")))
                .get()
                .uri("/all-clients")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = new String(response.getResponseBodyContent());
                    System.out.println("Response Body: " + responseBody);
                });
    }

    @Test
    void GetClientsByNameWithGoodScope() {
        this.rest.mutateWith(mockJwt().jwt((jwt) -> jwt.claim("scope", "message:readByNames")))
                .get()
                .uri("/get-clients-by-name?name=Alice")  // Dodaj parametr do URI
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = new String(response.getResponseBodyContent());
                    System.out.println("Response Body: " + responseBody);
                });
    }
    @Test
    void GetClientsByNameWithGoodScope2() {
        this.rest.mutateWith(mockJwt().jwt((jwt) -> jwt.claim("scope", "message:readByNames")))
                .get()
                .uri("/get-clients-by-name?name=Alice")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = new String(response.getResponseBodyContent());
                    System.out.println("Response Body: " + responseBody);
                });
    }
    @Test
    void GetClientsByNameWithBadScope2() {
        this.rest.mutateWith(mockJwt().jwt((jwt) -> jwt.claim("scope", "message:readAll")))
                .get()
                .uri("/get-clients-by-name?name=Alice")
                .exchange()
                .expectStatus()
                .isForbidden();
    }
    private Jwt.Builder jwt() {
        return Jwt.withTokenValue("token").header("alg", "SHA256");
    }
}


