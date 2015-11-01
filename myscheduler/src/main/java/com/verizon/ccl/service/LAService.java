package com.verizon.ccl.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LAService {
	
	private static Logger LOGGER = Logger.getLogger(LAService.class);
	
	public void loadLA() {
		LOGGER.debug("Executing LAService.loadLA start..." + Calendar.getInstance().getTime());
		System.out.println("Executing LAService.loadLA start..." + Calendar.getInstance().getTime());
		try {
			Thread.sleep(180000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.debug("Executing LAService.loadLA end..." + Calendar.getInstance().getTime());
		System.out.println("Executing LAService.loadLA end..." + Calendar.getInstance().getTime());
	}

}
