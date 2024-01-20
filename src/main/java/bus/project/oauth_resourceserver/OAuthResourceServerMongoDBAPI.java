package bus.project.oauth_resourceserver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Component
 interface OAuthResourceServerMongoDBAPI extends MongoRepository<ClientData, String> {

    @Query("{firstName:'?0'}")
    ClientData findItemByName(String firstName);


 }
