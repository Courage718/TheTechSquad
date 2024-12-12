package the_tech_squads.ou.task_manager.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import the_tech_squads.ou.task_manager.model.Task;
import the_tech_squads.ou.task_manager.service.TaskService;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public String inputTask(){
        return "userInputTask";
    }


    @PostMapping(value = "/task", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitTask(@ModelAttribute Task task, @SessionAttribute("userId") Long userId) {
        // Set the user ID in the task object
        task.setUserId(userId);

        // Save the task using the service
        taskService.save(task);

        return "redirect:/tasks"; // Redirect to a success page
    }


    @RequestMapping("/tasks")
    public String getTasksByUserId(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            // Redirect to login if no user is logged in
            return "redirect:/login";
        }

        // Fetch tasks for the logged-in user
        List<Task> tasks = taskService.getTasksByUserId(userId);
        model.addAttribute("tasks", tasks);

        return "task_list";
    }
}
