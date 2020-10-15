package com.abed.myquartzdemo.rest;


import com.abed.myquartzdemo.job.SampleJob;
import com.abed.myquartzdemo.service.SampleService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
public class WelcomeController {

    private static final Logger LOG = LoggerFactory.getLogger(SampleService.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @RequestMapping(value = "/scheduler/request", method = RequestMethod.GET, produces = "application/json")
    public Boolean portmanRequestFlow() {
        Scheduler scheduler = schedulerFactory.getScheduler();

        String jobName = "portman-request-flow";
        String groupName = "group1";

        JobDetail job = newJob(SampleJob.class)
              .withIdentity(jobName, groupName)
              .build();

        // Trigger the job to run now
        Trigger trigger = newTrigger()
                .withIdentity(jobName, groupName)
                .startNow()
                .build();

        // Tell quartz to schedule the job using our trigger
        try {
            scheduler.scheduleJob(job, trigger);
            return true;
        } catch (SchedulerException e) {
            LOG.info(e.getMessage());
            return false;
        }
    }
}
