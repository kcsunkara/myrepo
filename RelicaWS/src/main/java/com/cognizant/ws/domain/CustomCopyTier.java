package com.cognizant.ws.domain;

public class CustomCopyTier {

	private Long assetId;
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	private Long fileSize;
	private String sourcePath;
	private String destinationPath;
	private String hostName;
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	private Long assetInstanceId;
	private Long storageLocationId;
	private Long siteId;
	private String siteName;
	private String jobType;
	private String customerId;
	private String assetMD5;
	public String getAssetMD5() {
		return assetMD5;
	}

	public void setAssetMD5(String assetMD5) {
		this.assetMD5 = assetMD5;
	}

	private boolean md5Check;
	
	public boolean isMd5Check() {
		return md5Check;
	}

	public void setMd5Check(boolean md5Check) {
		this.md5Check = md5Check;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	private String fileName;
	private Integer CopyPoolLarge ;
	private Integer CopyPoolSmall;
	private Integer BatchPoolLarge;
	private Integer RestorePoolLarge;
	public Integer getRestorePoolLarge() {
		return RestorePoolLarge;
	}

	public void setRestorePoolLarge(Integer restorePoolLarge) {
		RestorePoolLarge = restorePoolLarge;
	}

	private Integer BatchPoolSmall;
	private String storageLocationType;
	private String FPRequestNumber;
	private Long jobId;
	private boolean isAssetsFound;
	
	public boolean isAssetsFound() {
		return isAssetsFound;
	}

	public void setAssetsFound(boolean isAssetsFound) {
		this.isAssetsFound = isAssetsFound;
	}

	public void setTier1StorageExists(boolean isTier1StorageExists) {
		this.isTier1StorageExists = isTier1StorageExists;
	}

	//Front Porch variables
	private String sessionCode;
	private String divaStatus;
	private String requestNumber;
	private String requestState;
//	private int actorInstances;
	private String actor;
	//private String actorInstanceId;
//	private int tapeInstances;
	private String appName;
	private String locName;
	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	private Long processId;
	private Long objectName;
	private Long objectCategory;
	private String abortCode;
	private String abortDesc;
	private String checkSumType;
	private boolean tapeInstancesExist;
	private boolean actorInstancesExist;
	
	
	
	public boolean isTapeInstancesExist() {
		return tapeInstancesExist;
	}

	public void setTapeInstancesExist(boolean tapeInstancesExist) {
		this.tapeInstancesExist = tapeInstancesExist;
	}

	public boolean isActorInstancesExist() {
		return actorInstancesExist;
	}

	public void setActorInstancesExist(boolean actorInstancesExist) {
		this.actorInstancesExist = actorInstancesExist;
	}

	public String getCheckSumType() {
		return checkSumType;
	}

	public void setCheckSumType(String checkSumType) {
		this.checkSumType = checkSumType;
	}

	public String getCheckSumValue() {
		return checkSumValue;
	}

	public void setCheckSumValue(String checkSumValue) {
		this.checkSumValue = checkSumValue;
	}

	private String checkSumValue;
	 
	private Long prioityLevel;
	private boolean isTier1StorageExists;
	private boolean reStoreJobFound;
	private String restoreOptions;
	
	
	
	public boolean getIsTier1StorageExists() {
		return isTier1StorageExists;
	}

	public void setIsTier1StorageExists(boolean isTier1StorageExists) {
		this.isTier1StorageExists = isTier1StorageExists;
	}

	public Long getPrioityLevel() {
		return prioityLevel;
	}

	public void setPrioityLevel(Long prioityLevel) {
		this.prioityLevel = prioityLevel;
	}

	public String getAbortCode() {
		return abortCode;
	}

	public void setAbortCode(String abortCode) {
		this.abortCode = abortCode;
	}

	public String getAbortDesc() {
		return abortDesc;
	}

	public void setAbortDesc(String abortDesc) {
		this.abortDesc = abortDesc;
	}

	public Long getObjectName() {
		return objectName;
	}

	public void setObjectName(Long objectName) {
		this.objectName = objectName;
	}

	public Long getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(Long objectCategory) {
		this.objectCategory = objectCategory;
	}

	public String getAppName() {
		return appName;
	}

	
	public Long getProcessId() {
		return processId;
	}



	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	public String getDivaStatus() {
		return divaStatus;
	}

	public void setDivaStatus(String divaStatus) {
		this.divaStatus = divaStatus;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getRequestState() {
		return requestState;
	}

	public void setRequestState(String requestState) {
		this.requestState = requestState;
	}

	
	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	
	public Integer getCopyPoolLarge() {
		return CopyPoolLarge;
	}

	public void setCopyPoolLarge(Integer copyPoolLarge) {
		CopyPoolLarge = copyPoolLarge;
	}

	public Integer getCopyPoolSmall() {
		return CopyPoolSmall;
	}

	public void setCopyPoolSmall(Integer copyPoolSmall) {
		CopyPoolSmall = copyPoolSmall;
	}

	public Integer getBatchPoolLarge() {
		return BatchPoolLarge;
	}

	public void setBatchPoolLarge(Integer batchPoolLarge) {
		BatchPoolLarge = batchPoolLarge;
	}

	public Integer getBatchPoolSmall() {
		return BatchPoolSmall;
	}

	public void setBatchPoolSmall(Integer batchPoolSmall) {
		BatchPoolSmall = batchPoolSmall;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "CustomCopyTier [assetId=" + assetId + ", fileSize=" + fileSize
				+ ", sourcePath=" + sourcePath + ", destinationPath="
				+ destinationPath + ", assetInstanceId=" + assetInstanceId
				+ ", storageLocationId=" + storageLocationId + ", siteId="
				+ siteId + ", siteName=" + siteName + ", jobType=" + jobType
				+ ", fileName=" + fileName + "]";
	}
	
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getDestinationPath() {
		return destinationPath;
	}
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	public Long getAssetInstanceId() {
		return assetInstanceId;
	}
	public void setAssetInstanceId(Long assetInstanceId) {
		this.assetInstanceId = assetInstanceId;
	}
	public Long getStorageLocationId() {
		return storageLocationId;
	}
	public void setStorageLocationId(Long storageLocationId) {
		this.storageLocationId = storageLocationId;
	}
	
	
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public void setStorageLocationType(String storageLocationType) {
		this.storageLocationType = storageLocationType;
		
	}

	public void setFPRequestNumber(String FPRequestNumber) {
		this.FPRequestNumber=FPRequestNumber;
		
	}
	
	public String getFPRequestNumber() {
		return FPRequestNumber;
	}

	public void setAppName(String appName) {
		this.appName=appName;
		
	}

	public void setProcessId(Long processId) {
		this.processId=processId;
		
	}
	
	public String getStorageLocationType() {
		return storageLocationType;
	}

	public void setReStoreJobFound(boolean reStoreJobFound) {
		this.reStoreJobFound=reStoreJobFound;
		
	}

	public boolean isReStoreJobFound() {
		return reStoreJobFound;
	}

	public String getRestoreOptions() {
		return restoreOptions;
	}

	public void setRestoreOptions(String restoreOptions) {
		this.restoreOptions = restoreOptions;
	}

	

}
