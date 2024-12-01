package the_tech_squads.ou.task_manager.nlp;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.util.Span;
import the_tech_squads.ou.task_manager.controller.TaskController;
import the_tech_squads.ou.task_manager.model.Task;
import the_tech_squads.ou.task_manager.service.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class nlpMain {
    String userInput;// input string coming from web
    String processedTask;//This will be the name of the task that appears on the website
    String date;//this will be used to place the task in a proper location
    String time;
    /*same purpose as the date; however if the calendar does not organize things by time (I.E. it only
    organizes by date), this should be included alongside the processedTask so that the user remembers what time
    they included with the task*/

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }
        Pipeline pipeline = new Pipeline(userInput);
        public String getProcessedTask(){
        return processedTask;
    }

    public static void main(String[] args) throws IOException{

        /*
        TaskController controller = new TaskController();
        String userInput = controller.saveTask();
         */

        //determines whether we want a task or a reminder; will determine whether we want to place something in
        //the calendar database or send it to the quartz scheduler to be triggered regularly as a reminder

        //temp scanner object for testing

        Scanner scanner = new Scanner(System.in);
        String userinput = scanner.nextLine();
        Pipeline pipeline = new Pipeline(userinput);

        pipeline.trainDoccatModel();

        String[] sentences = pipeline.breakSentences();

        String outputPrefix = "";

        for (String sentence : sentences) {

            //tokenize
            String[] tokens = pipeline.tokenize(sentence);
            System.out.println("\ntokens (the user's input broken down into an array of words after removing capital letters etc.):\n");
            for (int i = 0; i < tokens.length; i++) {
                System.out.print("["+tokens[i]+"],     ");
            }
            System.out.println("\n");
            //processing for date detection
            Span[] dateSpan = pipeline.dateRecognition(tokens);
            System.out.println("Date Spans(list of index ranges from the token array at which there are dates):\n");
            pipeline.spansToString(dateSpan);

            String formattedDate = pipeline.dateFormatting(dateSpan, tokens);
            System.out.println("The formatted date:   " + formattedDate);

            /*
            Span[] timeSpan = pipeline.timeRecognition(tokens);
            System.out.println(timeSpan);

            pipeline.spansToString(timeSpan);
            */

            //place star here/*

            //PROCESSING FOR CATEGORIZATION AND OUTPUT STATEMENT
            String[] POStags = pipeline.POSTag(tokens, pipeline.doccatModel);

            String[] lemmatizedTokens = pipeline.lemmatizeTokens(tokens, POStags);

            String category = pipeline.detectCategory(pipeline.doccatModel, lemmatizedTokens);

            if ("reminder".equals(category)){

                //add code to trigger quartz scheduler

            }
            else if ("task".equals(category)){


                //adds task to task database
                //Task task = new Task();

                //task.setName();
                //task.setDate();
                //task.setPriority();

                //TaskService taskService = new TaskService();
                //taskService.save(task);


            }

            //Build output statement for the user; gives the user confirmation of the task they set
            outputPrefix = pipeline.prefix.get(category);

            System.out.println("\nOutput statement to the user describing which type of action was taken:\n");
            System.out.println(outputPrefix);

            //place star here*/

        }

    }

}

