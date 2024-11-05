//package the_tech_squads.ou.task_manager.nlp;
//
//import opennlp.tools.sentdetect.SentenceDetectorME;
//import opennlp.tools.sentdetect.SentenceModel;
//import opennlp.tools.tokenize.TokenizerME;
//import opennlp.tools.tokenize.TokenizerModel;
//
//public class Pipeline {
//
//    public static String userInput;
//    public static String taskName;
//    public static String taskTime;
//
//
//    public Pipeline (String UserInput){
//
//        this.userInput = UserInput;
//
//    }
//
//    public static String[] breakSentences(){
//
//        SentenceDetectorME sentenceDetector = new SentenceDetectorME(new SentenceModel(/*figure out what goes in here*/));
//
//        String[] sentences = sentenceDetector.sentDetect(userInput);
//
//        return sentences;
//    }
//
//
//    public static String[] tokenize(String sentence){
//
//        TokenizerME tokenizer = new TokenizerME(new TokenizerModel(/*figure out what to put in here*/));
//
//        String[] tokens = tokenizer.tokenize(sentence);
//
//        return tokens;
//    }
//
//}