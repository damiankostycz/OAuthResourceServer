package bus.project.oauth_resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    public Map<String, String> greet() {
        var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Map.of("message", "Hello " + jwt.getSubject());
    }
}
