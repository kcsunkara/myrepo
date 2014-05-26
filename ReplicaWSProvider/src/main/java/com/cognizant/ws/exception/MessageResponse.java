package com.cognizant.ws.exception;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {
	

	private String title;
	private String code;
	private ErrorType errorType;
	private String exceptionName;
	private String message;
	private String description;
	private Long assetId;
	private Map<String, String> data;
	private List<Map<String, String>> dataList;
	private String returnFlag;
	private String signiantId;
	private String copyTierName;
	
	public String getSigniantId() {
		return signiantId;
	}

	public void setSigniantId(String signiantId) {
		this.signiantId = signiantId;
	}

	private String jobId;
	private String sourcePath;
	private String destinationPath;
	private String host;
	private String requestNumber;
	private String requestState;
	
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

	private String path;
	private String error;
	
	
	public List<Map<String, String>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, String>> dataList) {
		this.dataList = dataList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
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

	public MessageResponse() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * public String getTitle() { return title; }
	 * 
	 * public void setTitle(String title) { this.title = title; }
	 * 
	 * public ErrorType getErrorType() { return errorType; }
	 * 
	 * public void setErrorType(ErrorType errorType) { this.errorType =
	 * errorType; }
	 */

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	@Override
	public String toString() {
		/*
		 * return "ErrorMessage [title=" + title + ", code=" + code +
		 * ", errorType=" + errorType + ", exceptionName=" + exceptionName +
		 * ", message=" + message + "]";
		 */
		return "ErrorMessage [code=" + code + ", message=" + message
				+ ", decscription=" + description + "]";
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}
	
	public String getCopyTierName() {
		return copyTierName;
	}

	public void setCopyTierName(String copyTierName) {
		this.copyTierName = copyTierName;
	}
}