package com.cognizant.ui.json;

import java.util.List;

public class Customer {
	private long id;
	private String name;
	private String email;
	private List<Policy> policyList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Policy> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<Policy> policyList) {
		this.policyList = policyList;
	}

	@Override
	public String toString() {
		String customerString = "Customer ID ::" + getId()
				+ "\n Customer Name::" + getName() + "\n email:::" + getEmail()
				+ "\n PolicyList::::"
				+ getPolicyList();
		return customerString;
	}
}
