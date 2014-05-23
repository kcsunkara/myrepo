package com.cognizant.ws.domain;

import java.io.Serializable;

public class MD5Params implements Serializable{
	
	private Long batchId;
	
	private String paramMd5;
	
	private String errorCode;
	
	private String storageName;
	
	private Long storageId;
	
	private String hostName;


	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the storageName
	 */
	public String getStorageName() {
		return storageName;
	}

	/**
	 * @param storageName the storageName to set
	 */
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	/**
	 * @return the batchId
	 */
	public Long getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the paramMd5
	 */
	public String getParamMd5() {
		return paramMd5;
	}

	/**
	 * @param paramMd5 the paramMd5 to set
	 */
	public void setParamMd5(String paramMd5) {
		this.paramMd5 = paramMd5;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the storageId
	 */
	public Long getStorageId() {
		return storageId;
	}

	/**
	 * @param storageId the storageId to set
	 */
	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}
	
	
	

}
