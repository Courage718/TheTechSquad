package the_tech_squads.ou.task_manager.nlp;

import opennlp.tools.doccat.*;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
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
import java.util.HashMap;
import java.util.Map;

public class Pipeline {

    public String userInput;
    public String taskName;
    public String taskTime;
    public DoccatModel doccatModel;

    public Map<String, String> prefix = new HashMap<>();

    {
      //  prefix.

    }


    public Pipeline (String UserInput){

        this.userInput = UserInput;

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

        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File(/*include filename for model file*/));
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