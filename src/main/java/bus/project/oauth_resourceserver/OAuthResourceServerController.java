package bus.project.oauth_resourceserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@ResponseBody
public class OAuthResourceServerController {

    @Autowired
    private DataService dataService;
    @GetMapping("/all-clients")
     String getSecuredData() {
        return this.dataService.findAllClients().toString() ;
    }
    @GetMapping("/get-clients-by-name")
    public String getClientByName(@RequestParam String firstName) {
        ClientData receivedClient =  this.dataService.findItemByName(firstName);
        if(receivedClient == null) return "There is no client with name: " + firstName;
        else return  receivedClient.toString();
    }


}
