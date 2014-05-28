package com.cognizant.ui.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "assets")


public class Asset implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "fs_path")
	private String fsPath;
	
	@Column(name = "filesize")
	private Long filesize;

	@Column(name = "policy_id")
	private int policyId;

	@Column(name = "asset_md5")
	private String assetMD5;

	@Column(name = "user_md5")
	private String userMD5;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "delete_date")
	private Date deleteDate;

	private String customerName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFsPath() {
		return fsPath;
	}

	public void setFsPath(String fsPath) {
		this.fsPath = fsPath;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public String getAssetMD5() {
		return assetMD5;
	}

	public void setAssetMD5(String assetMD5) {
		this.assetMD5 = assetMD5;
	}

	public String getUserMD5() {
		return userMD5;
	}

	public void setUserMD5(String userMD5) {
		this.userMD5 = userMD5;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
