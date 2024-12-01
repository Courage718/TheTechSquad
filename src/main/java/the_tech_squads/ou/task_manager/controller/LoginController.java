package the_tech_squads.ou.task_manager.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import the_tech_squads.ou.task_manager.service.UserService;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;



    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login_page"; // Render login.html
    }

//
//    @PostMapping("/login")
//    public String login(@RequestParam String email, @RequestParam String password, Model model) {
//        // Call the service to check credentials
//        if (userService.authenticate(email, password)) {
//            // Successful login logic (e.g., redirect to dashboard)
//            return "redirect:/db-test-page";  // Change this to your success page
//        } else {
//            model.addAttribute("error", "Invalid email or password");
//            return "bye"; // Return to login page with error
//        }
//    }


    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Long userId = userService.authenticateAndGetUserId(email, password); // A method that returns user ID on successful authentication

        if (userId != null) {
            // Store userId in session
            session.setAttribute("userId", userId);
            return "redirect:/tasks";  // Redirect to tasks page
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Redirect back to login page with error
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Remove all session attributes
        return "redirect:/login"; // Redirect to the login page
    }

}
