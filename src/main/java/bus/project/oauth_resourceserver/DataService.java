package bus.project.oauth_resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService{

    @Autowired
    private OAuthResourceServerMongoDBAPI repository;

    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    public List<ClientData> findAllClients() {
        return repository.findAll();
    }

    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    public  ClientData findItemByName(String firstName) {
        return repository.findItemByName(firstName);
    }

}
