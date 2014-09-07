package com.hbo.itdmg.jobs;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class DigitalFusionLookupJob  extends QuartzJobBean {

	private static Logger LOG = Logger.getLogger(DigitalFusionLookupJob.class.getName());

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		LOG.debug("DigitalFusionLookupJob process initiated on: "+Calendar.getInstance().getTime());
	}

}
