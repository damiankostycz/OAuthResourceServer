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

    @GetMapping("/all-clients")
    public String getSecuredData() {
        return this.findAllClients().toString() ;
    }
    @GetMapping("/get-clients-by-name")
    public String getClientByName(@RequestParam String name) {
        return this.findClientByName(name).toString() ;
    }
    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }


    @Autowired
    private OAuthResourceServerMongoDBAPI repository;

    private List<ClientData> findAllClients() {
        return repository.findAll();
    }

    private  List<ClientData> findClientByName(String firstName) {
        return repository.findAllByFirstName(firstName);
    }


}
