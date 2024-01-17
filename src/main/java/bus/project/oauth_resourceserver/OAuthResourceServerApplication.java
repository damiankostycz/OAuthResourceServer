package bus.project.oauth_resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories

public class OAuthResourceServerApplication {

    private final OAuthResourceServerMongoDBAPI clientDataRepo;

    @Autowired
    public OAuthResourceServerApplication(OAuthResourceServerMongoDBAPI clientDataRepo) {
        this.clientDataRepo = clientDataRepo;
    }
    public static void main(String[] args) {
        SpringApplication.run(OAuthResourceServerApplication.class, args);

    }



}
