package com.cognizant.ui.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the asset_instances database table.
 * 
 */
@Entity
@Table(name = "asset_instances")
public class AssetInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "asset_id")
	private int assetId;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "encrypted")
	private boolean encrypted;

	@Column(name = "filename")
	private String filename;

	@Column(name = "last_check")
	private Date lastCheck;

	@Column(name = "location_md5")
	private String locationMD5;

	@Column(name = "path")
	private String path;

	@Column(name = "purge_date")
	private Date purgeDate;

	@Column(name = "status")
	private String status;

	@Column(name = "storage_location_id")
	private int storageLocationId;

	public AssetInstance() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(Date lastCheck) {
		this.lastCheck = lastCheck;
	}

	public String getLocationMD5() {
		return locationMD5;
	}

	public void setLocationMD5(String locationMD5) {
		this.locationMD5 = locationMD5;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getPurgeDate() {
		return purgeDate;
	}

	public void setPurgeDate(Date purgeDate) {
		this.purgeDate = purgeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStorageLocationId() {
		return storageLocationId;
	}

	public void setStorageLocationId(int storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

}