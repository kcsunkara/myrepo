package com.cognizant.ws.pojo;

/**
 * Comments
 */
import java.util.Date;
import java.util.List;

public class Asset implements java.io.Serializable{
	
	private Long id;

	private String name;
	
	private Long filesize;
	
	private Long policy_id;
	
	private String asset_md5;
	
	private String user_md5;
	
    private Date created_date;
	
	private Date  delete_date;
	
	private String fs_path;
	
	private Long customer_id;
	
	private String customer_name; 
	
	public List<AssetInstance> assetInstanceList;
	
	public List<AssetInstance> getAssetInstanceList() {
		return assetInstanceList;
	}
	public void setAssetInstanceList(List<AssetInstance> assetInstanceList) {
		this.assetInstanceList = assetInstanceList;
	}
	
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	public String getAsset_md5() {
		return asset_md5;
	}
	public void setAsset_md5(String asset_md5) {
		this.asset_md5 = asset_md5;
	}
	public String getUser_md5() {
		return user_md5;
	}
	public void setUser_md5(String user_md5) {
		this.user_md5 = user_md5;
	}
	public Date  getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date  created_date) {
		this.created_date = created_date;
	}
	public Date getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
	}

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
	
	public String getFs_path() {
		return fs_path;
	}

	public void setFs_path(String fsPath) {
		fs_path = fsPath;
	}

	public Long getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(Long policy_id) {
		this.policy_id = policy_id;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	
}
