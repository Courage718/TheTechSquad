package the_tech_squads.ou.task_manager.controller;

import the_tech_squads.ou.task_manager.nlp.Pipeline;

import org.springframework.web.bind.annotation.*;

import opennlp.tools.util.Span;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nlp")
public class nlpController {

    @PostMapping("/process")
    public Map<String, String> processInput(@RequestBody Map<String, String> request) {
        String userInput = request.get("input");
        Map<String, String> response = new HashMap<>();
        
        try {
            Pipeline pipeline = new Pipeline(userInput);
            pipeline.trainDoccatModel();  // Ensure models are loaded
            String[] sentences = pipeline.breakSentences();
            String[] tokens = pipeline.tokenize(sentences[0]);  // Process the first sentence for simplicity
            
            // Detect date and category
            List<Span> dateSpan = pipeline.dateRecognition(tokens);
            String formattedDate = pipeline.dateFormatting(dateSpan, tokens);
            String category = pipeline.detectCategory(pipeline.doccatModel, tokens);

            // Return response
            response.put("processedTask", category);
            response.put("date", formattedDate);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }

        return response;
    }
}
