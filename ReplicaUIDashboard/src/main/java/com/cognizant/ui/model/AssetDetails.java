package com.cognizant.ui.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class AssetDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String name;
	
	private String fsPath;
	
	private Long filesize;
	
	private int policyId;
	
	private String assetMD5;
	
	private String userMD5;
	
	private Date createdDate;
	
	private Date deleteDate;
	
	private String custName;
	
	private List<String> slNameList;
	
	private Date createDate;
	
	private boolean encrypted;
	
	private String filename;
	
	private Date lastCheck;
	
	private String locationMD5;
	
	private String path;
	
	private Date purgeDate;
	
	private String status;
	
	private int storageLocationId;
	
	private String value;

	private String storageLocationName;
	
	private Map<Long,String> storageLocationNamesMap;
	
	private String customerName;
	
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getStorageLocationName() {
		return storageLocationName;
	}

	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
	}

	public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Map<Long, String> getStorageLocationNamesMap() {
        return storageLocationNamesMap;
    }

    public void setStorageLocationNamesMap(Map<Long, String> storageLocationNamesMap) {
        this.storageLocationNamesMap = storageLocationNamesMap;
    }
    
    public List<String> getSlNameList() {
        return slNameList;
    }

    public void setSlNameList(List<String> slNameList) {
        this.slNameList = slNameList;
    }
    
}
