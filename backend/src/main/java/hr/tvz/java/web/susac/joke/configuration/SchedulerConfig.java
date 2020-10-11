package hr.tvz.java.web.susac.joke.configuration;

import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail verificationJobDetail(){
        return JobBuilder.newJob(VerificationJob.class).withIdentity("verificationJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger verificationJobTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(30).repeatForever();

        return TriggerBuilder.newTrigger().forJob(verificationJobDetail())
                .withIdentity("verificationTrigger")
                .withPriority(1)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
