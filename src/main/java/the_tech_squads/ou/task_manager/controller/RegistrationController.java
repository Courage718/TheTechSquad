package the_tech_squads.ou.task_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @GetMapping("/registration")
    public String goToRegistrationPage(){
        return "registration";
    }
    @PostMapping("/registration")
    public String register(@RequestParam String firstName, String lastName, String email, String password, String confirmPassword, Model model) {
        System.out.println(firstName);
        model.addAttribute("task", firstName);//test task in web
        return "bye";// This refers to html page
    }
}
