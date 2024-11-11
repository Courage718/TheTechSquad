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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pipeline {

    public String userInput;
    public String taskName;
    public String taskTime;
    public DoccatModel doccatModel;


    public Map<String, String> prefix = new HashMap<>();




    public Pipeline (String UserInput){

        this.userInput = UserInput;

        {
            prefix.put("reminder","Reminder to ");
            prefix.put("task","Scheduled task to ");

        }

    }

    public String[] breakSentences() throws IOException {

        try (InputStream model = new FileInputStream("en-sent.bin")){

            SentenceDetectorME sentenceDetector = new SentenceDetectorME(new SentenceModel(model));

            String[] sentences = sentenceDetector.sentDetect(userInput);

            return sentences;
        }
    }


    public String[] tokenize(String sentence) throws IOException{

        try(InputStream model = new FileInputStream("en-token.bin")) {

            TokenizerME tokenizer = new TokenizerME(new TokenizerModel(model));

            String[] tokens = tokenizer.tokenize(sentence);

            return tokens;

        }
    }

    public List<Span> dateRecognition(String[] tokens) throws IOException{

        try(InputStream model = new FileInputStream("en-ner-date.bin")) {

            NameFinderME nameFinder = new NameFinderME(new TokenNameFinderModel(model));

            List<Span> date = Arrays.asList(nameFinder.find(tokens));

            //test statements
            System.out.println(date);

            return date;
        }
    }

    public List<Span> timeRecognition(String[] tokens) throws IOException{

        try(InputStream model = new FileInputStream("en-ner-time.bin")) {

            NameFinderME nameFinder = new NameFinderME(new TokenNameFinderModel(model));

            List<Span> time = Arrays.asList(nameFinder.find(tokens));

            //test statements
            System.out.println(time);

            return time;
        }
    }

    public String spansToString(List<Span> spans, String[] tokens) {
        StringBuilder result = new StringBuilder();
        for (Span span : spans) {
            for (int i = span.getStart(); i < span.getEnd(); i++) {
                result.append(tokens[i]).append(" ");
            }
        }
        return result.toString().trim();
    }

    public String timeAsString(List<Span> timeSpans, String[] tokens) {
        StringBuilder timeBuilder = new StringBuilder();
        for (Span span : timeSpans) {
            for (int i = span.getStart(); i < span.getEnd(); i++) {
                timeBuilder.append(tokens[i]).append(" ");
            }
        }
        return timeBuilder.toString().trim();
    }

    public String[] POSTag(String[] tokens, DoccatModel doccatModel) throws IOException{

        try(InputStream model = new FileInputStream("en-pos-maxent.bin")) {

            POSTaggerME POSTagger = new POSTaggerME(new POSModel(model));

            String[] POSTags = POSTagger.tag(tokens);

            return POSTags;
        }
    }

    public String[] lemmatizeTokens(String[] tokens, String[] POSTags) throws IOException{

        try(InputStream model = new FileInputStream("en-lemmatizer.bin")) {

            LemmatizerME lemmatizer = new LemmatizerME(new LemmatizerModel(model));

            String[] lemmatizedTokens = lemmatizer.lemmatize(tokens, POSTags);

            return lemmatizedTokens;

        }
    }

    public void trainDoccatModel() throws IOException{

        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("task-categorizer.txt"));
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

}