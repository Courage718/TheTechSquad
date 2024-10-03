package the_tech_squads.ou.task_manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/hi")
    public String welcome(){
        return "sample_html_page";
    }
}
