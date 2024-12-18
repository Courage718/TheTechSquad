/*
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
    public static String userInput; // input string coming from web
    public static String processedTask; //This will be the name of the task that appears on the website
    public static String date; //this will be used to place the task in a proper location
    public static String time; //same purpose as the date

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    Pipeline pipeline = new Pipeline(userInput);

    public String getProcessedTask(){
        return processedTask;
    }

    public nlpMain(Task task){


        this.userInput = task.getDescription();

    }


    public static void runNLP() throws IOException{


        //TaskController controller = new TaskController();
        //String userInput = controller.saveTask();


        //determines whether we want a task or a reminder; will determine whether we want to place something in
        //the calendar database or send it to the quartz scheduler to be triggered regularly as a reminder

        //temp scanner object for testing
        Scanner scanner = new Scanner(System.in);
        userInput = scanner.nextLine();
        Pipeline pipeline = new Pipeline(userInput);

        pipeline.trainDoccatModel();

        String[] sentences = pipeline.breakSentences();

        String outputPrefix = "";

        for (String sentence : sentences) {

            //tokenize
            String[] tokens = pipeline.tokenize(sentence);

            //System.out.println("\ntokens (the user's input broken down into an array of words after removing capital letters etc.):\n");
            //for (int i = 0; i < tokens.length; i++) {
                System.out.print("["+tokens[i]+"],     ");
            //}
            //System.out.println("\n");


            //processing for date detection
            Span[] dateSpan = pipeline.dateRecognition(tokens);
            //System.out.println("Date Spans(list of index ranges from the token array at which there are dates):\n");
            String unformattedDate = pipeline.spansToString(dateSpan);

            date = pipeline.dateFormatting(dateSpan, tokens);
            //System.out.println("The formatted date:   " + formattedDate);


            Span[] timeSpan = pipeline.timeRecog(tokens);
            //System.out.println(timeSpan);

            time = pipeline.spansToString(timeSpan);




            //PROCESSING FOR CATEGORIZATION AND OUTPUT STATEMENT
            String[] POStags = pipeline.POSTag(tokens, pipeline.doccatModel);

            String[] lemmatizedTokens = pipeline.lemmatizeTokens(tokens, POStags);

            String category = pipeline.detectCategory(pipeline.doccatModel, lemmatizedTokens);



            //Build output statement for the user; gives the user confirmation of the task they set
            outputPrefix = pipeline.prefix.get(category);

            //System.out.println("\nOutput statement to the user describing which type of action was taken:\n");
            //System.out.println(outputPrefix);

            Span[] taskNameSpan = pipeline.taskNameRecognition(tokens);


            String taskName = pipeline.spansToString(taskNameSpan);

            processedTask = outputPrefix + taskName;

        }

        //adds task to task database

        Task task = new Task();
        task.setName(processedTask);
        //task.setDate(formattedDate);

        TaskService taskService = new TaskService();
        taskService.save(task);

    }

}
*/
