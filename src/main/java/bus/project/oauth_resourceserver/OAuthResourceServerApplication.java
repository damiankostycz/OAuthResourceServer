package bus.project.oauth_resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMongoRepositories
@EnableMethodSecurity
public class OAuthResourceServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(OAuthResourceServerApplication.class, args);

    }

}
