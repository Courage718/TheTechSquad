package the_tech_squads.ou.task_manager.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import the_tech_squads.ou.task_manager.nlp.Pipeline;

@RestController
@RequestMapping("/process-nlp")
public class nlpController {

    private final Pipeline pipeline;

    public nlpController() {
        this.pipeline = new Pipeline(null);
    }

    @PostMapping
    public NlpResponse processText(@RequestBody NlpRequest request) throws IOException {
        String userInput = request.getText();
        String processedTask = (pipeline).processTask(userInput);
        String date = pipeline.extractDate(userInput);  
        String category = pipeline.detectCategory(userInput);  

        return new NlpResponse(processedTask, date, category);
    }
}
