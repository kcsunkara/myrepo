package com.cognizant.ui.beans;

public class CustomPolicySiteInfo {
	
	Long siteId;
	String tier;
	String name;
	Boolean isPrimary;
	Integer requiredCopies;
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRequiredCopies() {
		return requiredCopies;
	}
	public void setRequiredCopies(Integer requiredCopies) {
		this.requiredCopies = requiredCopies;
	}
	public Boolean getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	
}
