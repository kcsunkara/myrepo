package com.cognizant.ui.json;

public class CompositeID {
	private long policy_id;
	private long site_id;

	public long getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(long policy_id) {
		this.policy_id = policy_id;
	}

	public long getSite_id() {
		return site_id;
	}

	public void setSite_id(long site_id) {
		this.site_id = site_id;
	}

	@Override
	public String toString() {
		String compositeIdString = "policy_id:::" + getPolicy_id() + "\n site_id"
				+ getSite_id();
		return compositeIdString;
	}
}
