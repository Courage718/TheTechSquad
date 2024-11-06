
package the_tech_squads.ou.task_manager;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class MyTaskJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Scheduled Task Executed!");
    }
}

