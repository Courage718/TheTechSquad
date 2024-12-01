package the_tech_squads.ou.task_manager.service;

import the_tech_squads.ou.task_manager.model.User;
import the_tech_squads.ou.task_manager.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        //System.out.println(users); // Add this line for debugging
        return users;
    }

    public User save(User user) {
        return userRepository.save(user);
    }




    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password); // Use hashed passwords in production
    }

    public Long authenticateAndGetUserId(String email, String password) {
        // Fetch user by email
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // If the password matches, return the user ID
            return user.getId();
        }

        // Return null if authentication fails
        return null;
    }


}
