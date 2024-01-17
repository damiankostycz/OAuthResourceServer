package bus.project.oauth_resourceserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
public class OAuthResourceServerController {
    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/secured-data")
    public String getSecuredData() {
        return this.findAllClients().toString() ;
    }
    @GetMapping("/getData")
    public String getClientByName(String name) {
        return this.findClientByName(name).toString() ;
    }
    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }
    @GetMapping("/message")
    public String message() {
        return "secret message";
    }

    @PostMapping("/message")
    public String createMessage(@RequestBody String message) {
        return String.format("Message was created. Content: %s", message);
    }

    @Autowired
    private OAuthResourceServerMongoDBAPI repository;

    private List<ClientData> findAllClients() {
        return repository.findAll();
    }

    private  List<ClientData> findClientByName(String firstName) {
        return repository.findAllByFirstName(firstName);
    }

    private List<ClientData> findAllByName(String firstName) {
        return repository.findAllByFirstName(firstName);
    }

}
