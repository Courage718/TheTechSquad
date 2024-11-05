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

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Pipeline {

    public static String userInput;
    public static String taskName;
    public static String taskTime;
    public DoccatModel doccatModel;

    public Map<String, String> prefix = new HashMap<>();

    {
        prefix.

    }


    public Pipeline (String UserInput){

        this.userInput = UserInput;

    }

    public String[] breakSentences(){

        SentenceDetectorME sentenceDetector = new SentenceDetectorME(new SentenceModel(/*input sentence detector model*/));

        String[] sentences = sentenceDetector.sentDetect(userInput);

        return sentences;
    }


    public String[] tokenize(String sentence){

        TokenizerME tokenizer = new TokenizerME(new TokenizerModel(/*input tokenizer model*/));

        String[] tokens = tokenizer.tokenize(sentence);

        return tokens;
    }

    public String[] POSTag(String[] tokens, DoccatModel doccatModel){

        POSTaggerME POSTagger = new POSTaggerME(new POSModel(/*input POStag model*/));

        String[] POSTags = POSTagger.tag(tokens);

        return POSTags;
    }

    public String[] lemmatizeTokens(String[] tokens, String[] POSTags){

        LemmatizerME lemmatizer = new LemmatizerME(new LemmatizerModel(/*input lemmatizer model*/));

        String[] lemmatizedTokens = lemmatizer.lemmatize(tokens, POSTags);

        return lemmatizedTokens;
    }

    public DoccatModel trainDoccatModel(){

        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File(/*include filename/path for model file*/));
        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sample = new DocumentSampleStream(lineStream);

        DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });

        TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, 0);

        // Train a model with classifications from above file.
        DoccatModel model = DocumentCategorizerME.train("en", sample, params, factory);

        return model;
    }

    public String detectCategory(DoccatModel model, String[] tokens){

        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);

        double[] probabilities = categorizer.categorize(tokens);
        String category = categorizer.getBestCategory(probabilities);

        return category;
    }

}
