package the_tech_squads.ou.task_manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import the_tech_squads.ou.task_manager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
