package the_tech_squads.ou.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import the_tech_squads.ou.task_manager.model.User;
import the_tech_squads.ou.task_manager.repo.UserRepository;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/registration")
    public String showForm(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }
//    @PostMapping("/registration")
//    public String register(@RequestParam String firstName,
//                           @RequestParam String lastName,
//                           @RequestParam String email,
//                           @RequestParam String password,
//                           @RequestParam String confirmPassword,
//                           Model model) {
//        System.out.println(firstName);
//        model.addAttribute("task", firstName);//test task in web
//        return "bye";// This refers to html page
//    }
    @PostMapping("/registration")
    public String submitForm(@ModelAttribute User user) {
        userRepository.save(user);
        return "bye";
    }
}
