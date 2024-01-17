package bus.project.oauth_resourceserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@EnableWebFluxSecurity
public class OAuthResourceServerSecurityConfig {
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET,"/secured-data").hasAuthority("SCOPE_message:read")
                        .pathMatchers(HttpMethod.GET, "/getData/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/message/**").hasAuthority("SCOPE_message:read")
                        .pathMatchers(HttpMethod.POST, "/message/**").hasAuthority("SCOPE_messag:write")
                        .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(withDefaults())
        );
        return http.build();
    }
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb+srv://damian:oRAhCnuhtl3g9138@cluster0.dzyi1is.mongodb.net/clients_data"));
    }

}

