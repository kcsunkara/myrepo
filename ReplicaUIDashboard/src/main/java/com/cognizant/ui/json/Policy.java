package com.cognizant.ui.json;

import java.util.List;

public class Policy {
   // @SerializedName("id")
	private long id;
	private boolean active;
	private long customer_id;
	private boolean encrypt;
	private String fs_path;
	private boolean md5_check;
	private String policy_name;
	private List<PolicySite> policySitesList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public String getFs_path() {
		return fs_path;
	}

	public void setFs_path(String fs_path) {
		this.fs_path = fs_path;
	}

	public boolean isMd5_check() {
		return md5_check;
	}

	public void setMd5_check(boolean md5_check) {
		this.md5_check = md5_check;
	}

	public String getPolicy_name() {
		return policy_name;
	}

	public void setPolicy_name(String policy_name) {
		this.policy_name = policy_name;
	}

	public List<PolicySite> getPolicySitesList() {
		return policySitesList;
	}

	public void setPolicySitesList(List<PolicySite> policySitesList) {
		this.policySitesList = policySitesList;
	}

	@Override
	public String toString() {
		String policyString = "PolicyID ::" + getId() + "\n active??" + isActive()
				+ "\n customer_id:::" + getCustomer_id() + "\n encrypt??"
				+ isEncrypt() + "\n fs_path:::" + getFs_path() + "\n md5_check:::"
				+ isMd5_check() + "\n policy_name:::" + getPolicy_name()
				+ "\n policySitesList:::" + getPolicySitesList();
		return policyString;
	}
}
