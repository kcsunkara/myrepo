package com.cognizant.ui.json;

public class Site {
	private long id;
	private String tier;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		String siteString = "Site ID :::" + getId() + "\n Tier::" + getTier()
				+ "\n Site Name :: " + getName();
		return siteString;
	}

}
