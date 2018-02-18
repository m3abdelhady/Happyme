package com.happy.me.jobs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.happy.me.jobs.exception.GlobalSchedulerException;
import com.happy.me.jobs.jobs.ResetJob;
import com.happy.me.jobs.manager.SchedulerManager;

/**
 * @author Mohamed.ElBayoumy@vodafone.com
 *
 */

@Component
public class Startup {
	


	@Autowired
	private SchedulerManager schedulerManager;

	@PostConstruct
	public void StartupEvent()  {
		System.out.println("Startup.StartupEvent()");
		try {
			schedulerManager.scheduleNewJob("RESET_JOB_KEY", "JOB_GROUP_RESET", ResetJob.class, schedulerManager.getSimpleTrigger("RESET_JOB_TRIGGER", 60));
//			schedulerManager.scheduleNewJob(CommonConstants.ARCHIVE_JOB_KEY, CommonConstants.JOB_GROUP_ARCHIVE, ArchiveAppointmentJob.class, schedulerManager.getDailyCronTrigger(CommonConstants.ARCHIVE_JOB_KEY, CommonConstants.JOB_GROUP_ARCHIVE, 0, 0, null, null));
		} catch (GlobalSchedulerException e) {
			e.printStackTrace();
//			LOGGER.error("[StartupEvent()] Faild while register rest job for first time", e);
		}
	}
	}