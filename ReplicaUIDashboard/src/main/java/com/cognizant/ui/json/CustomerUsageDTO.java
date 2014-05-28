package com.cognizant.ui.json;

public class CustomerUsageDTO {
    private String startDate;
    private String endDate;
    private String customerId;
    
    public String getCustomerId() {
    
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
    
        this.customerId = customerId;
    }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
