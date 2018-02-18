package com.happy.me.jobs.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.stereotype.Component;

import com.happy.me.service.JobService;

@Component
public class ResetJob implements Job {

	
	private JobService jobService;
	
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		System.out.println("ResetJob.execute()");
		try {
			SchedulerContext context = jec.getScheduler().getContext();
			jobService = (JobService) context.get("jobService");
			jobService.job();
			jobService.calculateExpirePoint();
		} catch (Exception e) {
		}
	}
}
