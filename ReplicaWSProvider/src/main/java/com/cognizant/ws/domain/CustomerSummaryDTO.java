package com.cognizant.ws.domain;

public class CustomerSummaryDTO {
	
	private Long totalAssets;
	private Long totalSizeOfAssets;
	private Customer customer;
	
	public Long getTotalAssets() {
		return totalAssets;
	}
	
	public void setTotalAssets(Long totalAssets) {
		this.totalAssets = totalAssets;
	}
	
	public Long getTotalSizeOfAssets() {
		return totalSizeOfAssets;
	}
	
	public void setTotalSizeOfAssets(Long totalSizeOfAssets) {
		this.totalSizeOfAssets = totalSizeOfAssets;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
