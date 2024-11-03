package the_tech_squads.ou.task_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import the_tech_squads.ou.task_manager.model.User;
import the_tech_squads.ou.task_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import the_tech_squads.ou.task_manager.nlp.nlpMain;

@Controller
public class TaskController {

    @Autowired
    private nlpMain nlpMain;
    @PostMapping("/task")
    public String addTask(@RequestParam String task, Model model) {
        nlpMain.setUserInput(task);
        model.addAttribute("task", task);//test task in web
        return "bye";// This refers to html page
    }


}
