package the_tech_squads.ou.task_manager.nlp;

import opennlp.tools.doccat.DoccatModel;
import the_tech_squads.ou.task_manager.controller.TaskController;
import the_tech_squads.ou.task_manager.model.Task;

import java.io.IOException;

public class nlpMain {
    String userInput;// input string coming from web
    String processedTask;//possible output

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }
        Pipeline pipeline = new Pipeline(userInput);
        public String getProcessedTask(){
        return processedTask;
    }

    public void runNLP() throws IOException{

        TaskController controller = new TaskController();
        String userInput = controller.saveTask();

        //determines whether we want a task or a reminder; will determine whether we want to place something in
        //the calendar database or send it to the quartz scheduler to be triggered regularly as a reminder
        Pipeline pipeline = new Pipeline(userInput);

        pipeline.trainDoccatModel();

        String[] sentences = pipeline.breakSentences();

        String outputPrefix = "";

        for (String sentence : sentences) {

            String[] tokens = pipeline.tokenize(sentence);

            //processing for date detection

            

            //processing for categorization
            String[] POStags = pipeline.POSTag(tokens, pipeline.doccatModel);

            String[] lemmatizedTokens = pipeline.lemmatizeTokens(tokens, POStags);

            String category = pipeline.detectCategory(pipeline.doccatModel, lemmatizedTokens);

            if ("reminder".equals(category)){

                //add code to trigger quartz scheduler

            }
            else if ("task".equals(category)){

                Task task = new Task();

                task.setName(/*task name*/);
                task.setDate(/*task date*/);
                task.setPriority(/*task priority*/);

                task.submitTask(task);

            }

            //Build output statement for the user; gives the user confirmation of the task they set
            outputPrefix = pipeline.prefix.get(category);





        }

    }

}

