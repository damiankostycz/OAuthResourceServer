package bus.project.oauth_resourceserver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
//METODY MUSZA MIEC TAKA NAZWE ABY BYLA ZGODNA Z NAZWAMI POL W DB CHYBA ZE UZYWASZ @QUERY
//BEZ QUERY nazywa metody jest tłumaczona na zapytanie na podstiwe nazwy
 interface OAuthResourceServerMongoDBAPI extends MongoRepository<ClientData, String> {
    List<ClientData> findAllByFirstName(String firstName);
    @Query("{firstName:'?0'}")
    ClientData findItemByName(String name);
}
