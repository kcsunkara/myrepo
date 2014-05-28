package com.cognizant.ui.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "storage_locations")
public class StorageLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "physical_name")
	private String physicalName;

	@Column(name = "storage_type")
	private String storageType;

	@Column(name = "name")
	private String name;

	@Column(name = "site_id")
	private int siteId;

	@Column(name = "path")
	private String path;

	@Column(name = "read_only")
	private boolean readOnly;

	@Column(name = "online")
	private boolean online;
	
	@Column(name="md5_pool_large")
	private Long md5PoolLarge;
	
	@Column(name="copy_pool_large")
	private Long copyPoolLarge;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhysicalName() {
		return physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Long getMd5PoolLarge() {
		return md5PoolLarge;
	}

	public void setMd5PoolLarge(Long md5PoolLarge) {
		this.md5PoolLarge = md5PoolLarge;
	}

	public Long getCopyPoolLarge() {
		return copyPoolLarge;
	}

	public void setCopyPoolLarge(Long copyPoolLarge) {
		this.copyPoolLarge = copyPoolLarge;
	}

}
