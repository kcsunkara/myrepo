package com.hbo.itdmg.jobs;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DigitalFusionLookupJob  implements StatefulJob { // implements StatefulJob {

	private static Logger LOG = Logger.getLogger(DigitalFusionLookupJob.class.getName());
	
	@Override
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false)
	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		LOG.debug("DigitalFusionLookupJob process initiated on: "+Calendar.getInstance().getTime());
		System.out.println("DigitalFusionLookupJob process initiated on: "+Calendar.getInstance().getTime());
		LOG.debug("FULL NAME: "+jobExecutionContext.getJobDetail().getFullName());
		LOG.debug("KEY NAME: "+jobExecutionContext.getJobDetail().getKey().getName());
		
		JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		int jobCount = dataMap.getInt("jobCount"); 
		//dataMap.put("jobCount",++jobCount);
		LOG.debug("DigitalFusionLookupJob JOB_COUNT: "+jobCount);
		System.out.println("DigitalFusionLookupJob JOB_COUNT: "+jobCount);
	}

}
