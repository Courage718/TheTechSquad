package the_tech_squads.ou.task_manager.nlp;

import opennlp.tools.doccat.*;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pipeline {

    public String userInput;
    public DoccatModel doccatModel;


    public Map<String, String> prefix = new HashMap<>();




    public Pipeline (String UserInput){

        this.userInput = UserInput;

        {
            prefix.put("reminder","Set a reminder.");
            prefix.put("task","Scheduled a task.");

        }

    }

    public String[] breakSentences() throws IOException {

        try (InputStream model = new FileInputStream("C:/Users/ggrae/OneDrive/Documents/GitHub/TheTechSquad/src/main/resources/models/en-sent.bin")){

            SentenceDetectorME sentenceDetector = new SentenceDetectorME(new SentenceModel(model));
            model.close();

            String[] sentences = sentenceDetector.sentDetect(userInput);

            return sentences;
        }
    }


    public String[] tokenize(String sentence) throws IOException{

        try(InputStream model = new FileInputStream("C:/Users/ggrae/OneDrive/Documents/GitHub/TheTechSquad/src/main/resources/models/en-token.bin")) {

            TokenizerME tokenizer = new TokenizerME(new TokenizerModel(model));
            model.close();

            String[] tokens = tokenizer.tokenize(sentence);

            return tokens;

        }
    }

    public Span[] dateRecognition(String[] tokens) throws IOException{

        try(InputStream model = new FileInputStream("C:/Users/ggrae/OneDrive/Documents/GitHub/TheTechSquad/src/main/resources/models/en-ner-date.bin")) {

            NameFinderME nameFinder = new NameFinderME(new TokenNameFinderModel(model));
            model.close();

            Span[] date = nameFinder.find(tokens);

            return date;
        }
    }

    private String removeOrdinalSuffixes(String dateString) {

        return dateString.replaceAll("\\b(\\d+)(st|nd|rd|th)\\b", "");

    }

    public String dateFormatting(Span[] dateSpan, String[] tokens){

        if (dateSpan.length == 0){
            System.out.println("The spans array is empty, no dates were detected");
            return null;
        }

        StringBuilder dateString = new StringBuilder();

        for (Span span : dateSpan) {
            for (int i = span.getStart(); i < span.getEnd()+1; i++) {
                dateString.append(tokens[i]).append(" ");
            }
        }

        String appendedDateString = removeOrdinalSuffixes(dateString.toString().trim());
        System.out.println("\nAppended date string to be sent to date parser:\n" + appendedDateString);

        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("MMMM d, yyyy"),
                DateTimeFormatter.ofPattern("MMMM d yyyy"),
                DateTimeFormatter.ofPattern("MMMM d"),
                //add more date formats
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate parsedDate = LocalDate.parse(appendedDateString, formatter);
                return parsedDate.format(DateTimeFormatter.ISO_LOCAL_DATE); // Format as YYYY-MM-DD
            }
            catch (DateTimeParseException e) {
                System.out.println("Date format not recognized, trying next format");
            }
        }

        return null;

    }

    public Span[] timeRecog(String[] tokens) throws IOException{

        try(InputStream model = new FileInputStream("C:/Users/ggrae/OneDrive/Documents/GitHub/TheTechSquad/src/main/resources/models/en-ner-time.bin")) {
            NameFinderME nameFinder = new NameFinderME(new TokenNameFinderModel(model));
            model.close();

            Span[] time = nameFinder.find(tokens);

            return time;
        }
    }

    public void spansToString(Span[] timeSpans) {

        System.out.println("Spans: [");
        for (Span span : timeSpans) {
            for (int i = span.getStart(); i < span.getEnd(); i++) {
                System.out.print(span.toString() + " ");
            }
        }
        System.out.print("]\n\n");
    }

    //place star here/*

    public String[] POSTag(String[] tokens, DoccatModel doccatModel) throws IOException{

        try(InputStream model = new FileInputStream("C:/Users/ggrae/OneDrive/Documents/GitHub/TheTechSquad/src/main/resources/models/en-pos-maxent.bin")) {

            POSTaggerME POSTagger = new POSTaggerME(new POSModel(model));
            model.close();

            String[] POSTags = POSTagger.tag(tokens);

            return POSTags;
        }
    }

    public String[] lemmatizeTokens(String[] tokens, String[] POSTags) throws IOException{

        try(InputStream model = new FileInputStream("C:/Users/ggrae/OneDrive/Documents/GitHub/TheTechSquad/src/main/resources/models/en-lemmatizer.bin")) {

            LemmatizerME lemmatizer = new LemmatizerME(new LemmatizerModel(model));
            model.close();

            String[] lemmatizedTokens = lemmatizer.lemmatize(tokens, POSTags);

            return lemmatizedTokens;

        }
    }



    public void trainDoccatModel() throws IOException{

        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("C:/Users/ggrae/OneDrive/Documents/GitHub/TheTechSquad/src/main/resources/models/task-categorizer.txt"));
        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sample = new DocumentSampleStream(lineStream);

        DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });

        TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, 0);

        // Train a model with classifications from above file.
        DoccatModel model = DocumentCategorizerME.train("en", sample, params, factory);

        this.doccatModel = model;
    }

    public String detectCategory(DoccatModel model, String[] tokens){

        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);

        double[] probabilities = categorizer.categorize(tokens);
        String category = categorizer.getBestCategory(probabilities);

        return category;
    }

    public String extractDate(String userInput2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractDate'");
    }

    public String detectCategory(String userInput2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'detectCategory'");
    }

    public String processTask(String userInput2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processTask'");
    }

    //place star here*/


}