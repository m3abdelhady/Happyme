package com.happy.me.jobs.manager;

import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.happy.me.jobs.exception.GlobalSchedulerException;



@Component
public class SchedulerManager {

	@Autowired
	private Scheduler scheduler;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void scheduleNewJob(String key, String jobGroup, Class klass, Trigger trigger)
			throws GlobalSchedulerException {
		try {
			JobDataMap dataMap = new JobDataMap();
//			dataMap.put("test", accountId);
			JobKey jobKey = new JobKey(key, jobGroup);
			
			if(scheduler.checkExists(jobKey))
				scheduler.deleteJob(jobKey);
			
			JobDetail job = JobBuilder.newJob(klass).setJobData(dataMap).withIdentity(jobKey).build();
			scheduler.scheduleJob(job, trigger);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new GlobalSchedulerException();
		}
	}
	
	public void deleteJobsByGroupName(String groupName) throws GlobalSchedulerException{
		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(groupName);
		try {
			 Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			 for(JobKey jobKey : jobKeys){
				 scheduler.deleteJob(jobKey);
			 }
		} catch (SchedulerException e) {
			throw new GlobalSchedulerException();
		}
	}

	public Trigger getSimpleTrigger(String key, int waitIntervalSeconds)
			throws GlobalSchedulerException {

		try {
			unscheduleJob(key);
		} catch (Exception ex) {
			throw new GlobalSchedulerException();
		}

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(key)
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(waitIntervalSeconds)
								.repeatForever()).build();

		return trigger;
	}

	public Trigger getCronTrigger(String key, String cronExpression)
			throws GlobalSchedulerException {

		try {
			unscheduleJob(key);
		} catch (Exception ex) {
			throw new GlobalSchedulerException();
		}

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(key)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();

		return trigger;
	}
	
	public Trigger getPeriodicalCronTrigger(String key, String groupName, int dayOfMonth, int dayOfWeek, int hour, int min,
			String contextVariableKey, String contextVariableValue) throws GlobalSchedulerException{
		if(dayOfMonth != -1)
			return getMontlyCronTrigger(key, groupName, dayOfMonth, hour, min, contextVariableKey, contextVariableValue);
		else if(dayOfWeek != -1)
			return getWeeklyCronTrigger(key, groupName, dayOfWeek, hour, min, contextVariableKey, contextVariableValue);
		else 
			return getDailyCronTrigger(key, groupName, hour, min, contextVariableKey, contextVariableValue);
	}

	public Trigger getDailyCronTrigger(String key, String groupName, int hour, int min,
			String contextVariableKey, String contextVariableValue)
			throws GlobalSchedulerException {

		try {
			unscheduleJob(key);
			if(contextVariableKey != null){
				scheduler.getContext()
						.put(contextVariableKey, contextVariableValue);
			}
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					//.withIdentity(key)
					.withIdentity(new TriggerKey(key, groupName))
					.withSchedule(
							CronScheduleBuilder.dailyAtHourAndMinute(hour, min))
					.build();

			return trigger;
		} catch (Exception ex) {
			throw new GlobalSchedulerException();
		}
	}
	
	public Trigger getWeeklyCronTrigger(String key, String groupName, int dayOfWeek , int hour, int min,
			String contextVariableKey, String contextVariableValue)
			throws GlobalSchedulerException {

		try {
			unscheduleJob(key);
			if(contextVariableKey != null){
				scheduler.getContext()
						.put(contextVariableKey, contextVariableValue);
			}
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					//.withIdentity(key)
					.withIdentity(new TriggerKey(key, groupName))
					.withSchedule(
							CronScheduleBuilder.weeklyOnDayAndHourAndMinute(dayOfWeek,hour, min))
					.build();

			return trigger;
		} catch (Exception ex) {
			throw new GlobalSchedulerException();
		}
	}
	
	public Trigger getMontlyCronTrigger(String key , String groupName, int dayOfMonth , int hour, int min,
			String contextVariableKey, String contextVariableValue)
			throws GlobalSchedulerException {

		try {
			unscheduleJob(key);
			if(contextVariableKey != null){
				scheduler.getContext()
						.put(contextVariableKey, contextVariableValue);
			}
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					//.withIdentity(key)
					.withIdentity(new TriggerKey(key, groupName))
					.withSchedule(
							CronScheduleBuilder.monthlyOnDayAndHourAndMinute(dayOfMonth,hour, min))
					.build();

			return trigger;
		} catch (Exception ex) {
			throw new GlobalSchedulerException();
		}
	}

	public void unscheduleJob(String triggerKey) throws GlobalSchedulerException {
		try {
			TriggerKey key = new TriggerKey(triggerKey);
			Trigger trigger = scheduler.getTrigger(key);
			if (trigger != null)
				scheduler.unscheduleJob(key);
		} catch (Exception ex) {
			throw new GlobalSchedulerException();
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	
}
