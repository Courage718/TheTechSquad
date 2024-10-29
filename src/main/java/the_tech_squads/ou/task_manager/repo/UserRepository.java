package the_tech_squads.ou.task_manager.repo;

import the_tech_squads.ou.task_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}