package the_tech_squads.ou.task_manager.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import the_tech_squads.ou.task_manager.model.Task;
import the_tech_squads.ou.task_manager.service.TaskService;

@Controller
public class TaskController {

   @Autowired
   private TaskService taskService;

    @GetMapping("/task")
    public String inputTask(){
        return "userInputTask";
    }


    @PostMapping(value = "/task", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitTask(@ModelAttribute Task task) {
       taskService.save(task);
        return "bye";
    }

}
