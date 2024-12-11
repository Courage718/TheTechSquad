package the_tech_squads.ou.task_manager.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import the_tech_squads.ou.task_manager.nlp.Pipeline;

@RestController
@RequestMapping("/process-nlp")
public class nlpController {

    private final Pipeline pipeline;

    public nlpController() {
        // Initialize your NLP pipeline here, if necessary
        this.pipeline = new Pipeline(null);
    }

    @PostMapping
    public NlpResponse processText(@RequestBody NlpRequest request) throws IOException {
        // Use the NLP pipeline to process the input text
        String userInput = request.getText();

        // Assuming your NLP pipeline processes the text and returns appropriate results
        String processedTask = (pipeline).processTask(userInput);
        String date = pipeline.extractDate(userInput);  // This should be part of your NLP pipeline
        String category = pipeline.detectCategory(userInput);  // Based on your NLP pipeline

        // Create and return the response
        return new NlpResponse(processedTask, date, category);
    }
}
