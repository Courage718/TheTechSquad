package the_tech_squads.ou.task_manager.controller;

public class NlpResponse {
    private String processedTask;
    private String date;
    private String category;

    // Constructor, Getters, and Setters
    public NlpResponse(String processedTask, String date, String category) {
        this.processedTask = processedTask;
        this.date = date;
        this.category = category;
    }

    public String getProcessedTask() {
        return processedTask;
    }

    public void setProcessedTask(String processedTask) {
        this.processedTask = processedTask;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
