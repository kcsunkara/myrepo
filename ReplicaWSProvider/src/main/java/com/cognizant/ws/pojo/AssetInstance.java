package com.cognizant.ws.pojo;

import java.util.Date;

public class AssetInstance implements java.io.Serializable {

	private Long id;

	private Long asset_id;

	private String storageLocationId;

	private String filename;

	private String location_md5;

	private Date create_date;

	private Date last_check;

	private Date purge_date;

	private String status = "N";

	private String encrypted = "";
	
	private String base_path;
	
	private String signiant_id;
	
	public Long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Long asset_id) {
		this.asset_id = asset_id;
	}

	public String getLocation_md5() {
		return location_md5;
	}

	public void setLocation_md5(String location_md5) {
		this.location_md5 = location_md5;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getLast_check() {
		return last_check;
	}

	public void setLast_check(Date last_check) {
		this.last_check = last_check;
	}

	public Date getPurge_date() {
		return purge_date;
	}

	public void setPurge_date(Date purge_date) {
		this.purge_date = purge_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStorageLocationId() {
		return storageLocationId;
	}

	public void setStorageLocationId(String storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBase_path() {
		return base_path;
	}

	public void setBase_path(String base_path) {
		this.base_path = base_path;
	}

	public String getSigniant_id() {
		return signiant_id;
	}

	public void setSigniant_id(String signiant_id) {
		this.signiant_id = signiant_id;
	}

}
