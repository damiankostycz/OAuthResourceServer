package bus.project.oauth_resourceserver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
 interface OAuthResourceServerMongoDBAPI extends MongoRepository<ClientData, String> {

    @Query("{firstName:'?0'}")
    ClientData findItemByName(String firstName);

    List<ClientData> findAllClients();
}
