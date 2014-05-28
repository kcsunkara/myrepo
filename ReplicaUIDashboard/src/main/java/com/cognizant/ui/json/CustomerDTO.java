package com.cognizant.ui.json;

public class CustomerDTO {
	private long totalAssets;
	private double totalSizeOfAssets;
	private Customer customer;

	public long getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(long totalAssets) {
		this.totalAssets = totalAssets;
	}

	public double getTotalSizeOfAssets() {
		return totalSizeOfAssets;
	}

	public void setTotalSizeOfAssets(double totalSizeOfAssets) {
		this.totalSizeOfAssets = totalSizeOfAssets;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String toString() {
		String customerDtoString = "Total Assets ::" + getTotalAssets()
				+ "\n totalSizeOfAssets::" + getTotalSizeOfAssets()
				+ "\n Customer:::" + getCustomer().toString();
		return customerDtoString;
	}

}
