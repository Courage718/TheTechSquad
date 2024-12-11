package the_tech_squads.ou.task_manager.controller;

import java.util.Objects;

public class nlpController {
    private final String task;
    private final String date;
    private final String category;

    // Constructor for initializing all fields
    public nlpController(String task, String date, String category) {
        this.task = task;
        this.date = date;
        this.category = category;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "NlpResponse{" +
               "task='" + task + '\'' +
               ", date='" + date + '\'' +
               ", category='" + category + '\'' +
               '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        nlpController that = (nlpController) o;
        return task.equals(that.task) && date.equals(that.date) && category.equals(that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, date, category);
    }
}
