package com.cognizant.ui.json;

import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AssetDTO {
	
	private static final Logger LOG = Logger.getLogger(AssetDTO.class);
	
	private Long id;
	public List<AssetInstancesDTO> assetInstances;
	private String name;
	private Long filesize;
	private Long policy_id;
	private String asset_md5;
	private String user_md5;
    private String created_date;
	private String  delete_date;
	private String fs_path;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<AssetInstancesDTO> getAssetInstances() {
		return assetInstances;
	}
	public void setAssetInstances(List<AssetInstancesDTO> assetInstances) {
		this.assetInstances = assetInstances;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	public Long getPolicy_id() {
		return policy_id;
	}
	public void setPolicy_id(Long policy_id) {
		this.policy_id = policy_id;
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
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}
	public String getFs_path() {
		return fs_path;
	}
	public void setFs_path(String fs_path) {
		this.fs_path = fs_path;
	}
	
}
