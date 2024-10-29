package the_tech_squads.ou.task_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import the_tech_squads.ou.task_manager.model.User;
import the_tech_squads.ou.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userList"; // This refers to userList.html
    }



    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}