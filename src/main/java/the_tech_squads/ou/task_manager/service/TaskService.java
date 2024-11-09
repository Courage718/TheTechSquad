package the_tech_squads.ou.task_manager.service;

import the_tech_squads.ou.task_manager.model.Task;
import the_tech_squads.ou.task_manager.model.User;
import the_tech_squads.ou.task_manager.repo.TaskRepository;
import the_tech_squads.ou.task_manager.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        List<Task> tasks = taskRepository.findAll();
        //System.out.println(tasks); // Add this line for debugging
        return tasks;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }





}
