package com.verizon.ccl.jobs;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.verizon.ccl.service.LAService;

public class LALoader implements Job {
	
	private static Logger LOGGER = Logger.getLogger(LALoader.class);

	@Autowired
	LAService laService;
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false)
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		System.out.println("LALoader Job initiated on: " + Calendar.getInstance().getTime());
		System.out.println("FULL NAME: " + jobExecutionContext.getJobDetail().getFullName());
		System.out.println("KEY NAME: " + jobExecutionContext.getJobDetail().getKey().getName());
		
		LOGGER.debug("LALoader Job initiated on: " + Calendar.getInstance().getTime());
		LOGGER.debug("FULL NAME: " + jobExecutionContext.getJobDetail().getFullName());
		LOGGER.debug("KEY NAME: " + jobExecutionContext.getJobDetail().getKey().getName());
		
		laService.loadLA();
		
		/*JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		int jobCount = dataMap.getInt("jobCount"); 
		//dataMap.put("jobCount",++jobCount);
		LOGGER.debug("LALoader JOB_COUNT: " + jobCount);
		System.out.println("LALoader JOB_COUNT: " + jobCount);*/
		
	}

}
