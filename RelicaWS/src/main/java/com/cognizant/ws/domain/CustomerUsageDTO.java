package com.cognizant.ws.domain;

import java.sql.Date;
import java.util.Map;

public class CustomerUsageDTO {
	
	Date date;
	Map<String, Long> storageUsage;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Map<String, Long> getStorageUsage() {
		return storageUsage;
	}
	public void setStorageUsage(Map<String, Long> storageUsage) {
		this.storageUsage = storageUsage;
	}
}
