package com.cognizant.ui.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the policies database table.
 * 
 */
@Entity
@Table(name="policies")
@NamedQueries(
		{@NamedQuery(name = "allPolicies", query = "from  Policy order by id desc")})
public class Policy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="active")
	private boolean active;

	@Column(name="customer_id")
	private int customerId;
	
	@Column(name="encrypt")
	private boolean encrypt;

	@Column(name="fs_path")
	private String fsPath;

	@Column(name="md5_check")
	private boolean md5Check;

	@Column(name="policy_name")
	private String policyName;

	public Policy() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public String getFsPath() {
		return fsPath;
	}

	public void setFsPath(String fsPath) {
		this.fsPath = fsPath;
	}

	public boolean isMd5Check() {
		return md5Check;
	}

	public void setMd5Check(boolean md5Check) {
		this.md5Check = md5Check;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

}