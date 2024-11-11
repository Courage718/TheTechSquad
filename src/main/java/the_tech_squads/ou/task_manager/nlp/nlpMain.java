package the_tech_squads.ou.task_manager.nlp;

import opennlp.tools.doccat.DoccatModel;

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
        String userInput = controller.addTask();

        Pipeline pipeline = new Pipeline(userInput);

        pipeline.trainDoccatModel();

        String[] sentences = pipeline.breakSentences();

        String outputPrefix = "";

        for (String sentence : sentences) {

            String[] tokens = pipeline.tokenize(sentence);

            String[] POStags = pipeline.POSTag(tokens, pipeline.doccatModel);

            String[] lemmatizedTokens = pipeline.lemmatizeTokens(tokens, POStags);

            String category = pipeline.detectCategory(pipeline.doccatModel, lemmatizedTokens);

            //get task prefix for specific task categories
            outputPrefix = pipeline.prefix.get(category);

            //test statement
            System.out.println(outputPrefix);

        }

    }

}

