package com.abed.myquartzdemo.job;

import com.abed.myquartzdemo.service.SampleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SampleJob implements Job {
    @Autowired
    private SampleService service;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        service.hello();
    }
}
