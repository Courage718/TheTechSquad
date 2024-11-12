package the_tech_squads.ou.task_manager;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagerApplication {

	/*
	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(TaskManagerApplication.class, args);
		scheduleMyTask();
	}
	*/

	public static void scheduleMyTask() throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		JobDetail job = JobBuilder.newJob(MyTaskJob.class)
				.withIdentity("myTaskJob", "group1")
				.build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("myTrigger", "group1")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(10)
						.repeatForever())
				.build();

		scheduler.start();
		scheduler.scheduleJob(job, trigger);

	}
}