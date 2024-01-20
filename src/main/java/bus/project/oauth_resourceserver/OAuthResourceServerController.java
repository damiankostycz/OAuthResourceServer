package bus.project.oauth_resourceserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class OAuthResourceServerController {

    @Autowired
    DataService dataService;
    @GetMapping("/all-clients")
    public String getSecuredData() {
        return this.dataService.findAllClients().toString() ;
    }
    @GetMapping("/get-clients-by-name")
    public String getClientByName(@RequestParam String firstName) {
        return this.dataService.findItemByName(firstName).toString() ;
    }
    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }




}
