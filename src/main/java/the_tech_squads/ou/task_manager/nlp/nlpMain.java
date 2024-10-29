package the_tech_squads.ou.task_manager.nlp;

import the_tech_squads.ou.task_manager.controller.TaskController;

public class nlpMain {

    public static void main(String[] args){

        TaskController controller = new TaskController();

        String userInput = controller.addTask();

        Pipeline pipeline = new Pipeline(userInput);

    }

}
