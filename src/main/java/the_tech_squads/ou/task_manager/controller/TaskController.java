package the_tech_squads.ou.task_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import the_tech_squads.ou.task_manager.nlp.nlpMain;

@Controller
public class TaskController {

   //@Autowired
//    private nlpMain nlpMain;

    @GetMapping("/task")
    public String inputTask(){
        return "userInputTask";
    }
    @PostMapping("/task")
    public String addTask(@RequestParam String taskName,
                          @RequestParam String taskDescription,
                          @RequestParam String dueDate,
                          @RequestParam String priority,
                                      Model model) {
        //nlpMain.setUserInput(userInput);
        System.out.println(taskDescription);
        System.out.println(dueDate);
        System.out.println(priority);
        model.addAttribute("task", taskName);//test task in web
        return "bye";// This refers to html page
    }
}
