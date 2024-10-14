package the_tech_squads.ou.task_manager;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome(){
        return "index";
    }


}