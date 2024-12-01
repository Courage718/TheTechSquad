package the_tech_squads.ou.task_manager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome(){
        return "index";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }


}