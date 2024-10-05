package the_tech_squads.ou.task_manager;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyTaskJob implements Job {
    public MyTaskJob() {
    }

    public void execute() throws JobExecutionException {
        execute(null);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Scheduled Task Executed!");
    }
}
