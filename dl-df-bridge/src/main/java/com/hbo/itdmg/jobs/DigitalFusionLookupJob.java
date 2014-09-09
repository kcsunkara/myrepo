package com.hbo.itdmg.jobs;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class DigitalFusionLookupJob  extends QuartzJobBean { // implements StatefulJob {

	private static Logger LOG = Logger.getLogger(DigitalFusionLookupJob.class.getName());
	
	@Override
	@Transactional //(propagation = Propagation.REQUIRED, readOnly = false)
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOG.debug("DigitalFusionLookupJob process initiated on: "+Calendar.getInstance().getTime());
		System.out.println("DigitalFusionLookupJob process initiated on: "+Calendar.getInstance().getTime());
	}

}
