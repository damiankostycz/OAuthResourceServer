package bus.project.oauth_resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DataService {

    @Autowired
    OAuthResourceServerMongoDBAPI clientRepo;
    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    public List<ClientData>  findAllClients() {
        return clientRepo.findAll();
    }

    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    public  ClientData findItemByName(String firstName) {
        return clientRepo.findItemByName(firstName);
    }

}
