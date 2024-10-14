package the_tech_squads.ou.task_manager;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DBController {
    @GetMapping("/db")
    public String dbtest(Model model){
        model.addAttribute("Hello, this is model test");
        return "db-test-page";
    }
    @GetMapping("/bye")
    public String bye(Model model){
        //model.addText(null);
        return "bye";
    }
}
