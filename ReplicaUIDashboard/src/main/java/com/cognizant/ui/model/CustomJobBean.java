package com.cognizant.ui.model;


public class CustomJobBean {
	
	private int MD5RunningCount;
	private int MD5CompletedCount;
	private int MD5ErrorCount;
	
	private int CopyTier1RunningCount;
	private int CopyTier1CompletedCount;
	private int CopyTier1ErrorCount;
	
	private int md5Total;
	private int copytier1Total;
	private int copytier2Total;
	private int restoreTotal;
	
	private int totalRunning;
	private int totalFailed;
	private int totalCompleted;
	
	private int finalTotal;
	
	private Long jobId;
	private String job_type;
	private Long assetId;
	private String start_date;
	private String end_date;
	private String error_message;
	private String job_status;
	private String code;
	private String fileName;
	private String storageName;
	private String requestHostName;
	private int policyId;
	private String name;
	private int invalidAssetCount;

	public int getMD5RunningCount() {
		return MD5RunningCount;
	}
	public void setMD5RunningCount(int mD5RunningCount) {
		this.MD5RunningCount = mD5RunningCount;
	}
	public int getMD5CompletedCount() {
		return MD5CompletedCount;
	}
	public void setMD5CompletedCount(int mD5CompletedCount) {
		this.MD5CompletedCount = mD5CompletedCount;
	}
	public int getMD5ErrorCount() {
		return MD5ErrorCount;
	}
	public void setMD5ErrorCount(int mD5ErrorCount) {
		this.MD5ErrorCount = mD5ErrorCount;
	}
	
	public int getCopyTier1RunningCount() {
		return CopyTier1RunningCount;
	}
	public void setCopyTier1RunningCount(int copyTier1RunningCount) {
		this.CopyTier1RunningCount = copyTier1RunningCount;
	}
	public int getCopyTier1CompletedCount() {
		return CopyTier1CompletedCount;
	}
	public void setCopyTier1CompletedCount(int copyTier1CompletedCount) {
		this.CopyTier1CompletedCount = copyTier1CompletedCount;
	}
	public int getCopyTier1ErrorCount() {
		return CopyTier1ErrorCount;
	}
	public void setCopyTier1ErrorCount(int copyTier1ErrorCount) {
		this.CopyTier1ErrorCount = copyTier1ErrorCount;
	}
	
	public int getMd5Total() {
		return md5Total;
	}
	public void setMd5Total(int md5Total) {
		this.md5Total = md5Total;
	}
	public int getCopytier1Total() {
		return copytier1Total;
	}
	public void setCopytier1Total(int copytier1Total) {
		this.copytier1Total = copytier1Total;
	}
	public int getCopytier2Total() {
		return copytier2Total;
	}
	public void setCopytier2Total(int copytier2Total) {
		this.copytier2Total = copytier2Total;
	}
	public int getRestoreTotal() {
		return restoreTotal;
	}
	public void setRestoreTotal(int restoreTotal) {
		this.restoreTotal = restoreTotal;
	}
	public int getTotalRunning() {
		return totalRunning;
	}
	public void setTotalRunning(int totalRunning) {
		this.totalRunning = totalRunning;
	}
	public int getTotalFailed() {
		return totalFailed;
	}
	public void setTotalFailed(int totalFailed) {
		this.totalFailed = totalFailed;
	}
	public int getTotalCompleted() {
		return totalCompleted;
	}
	public void setTotalCompleted(int totalCompleted) {
		this.totalCompleted = totalCompleted;
	}

	public int getFinalTotal() {
		return finalTotal;
	}
	public void setFinalTotal(int finalTotal) {
		this.finalTotal = finalTotal;
	}
	
	public String getRequestHostName() {
		return requestHostName;
	}
	public void setRequestHostName(String requestHostName) {
		this.requestHostName = requestHostName;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJob_type() {
		return job_type;
	}
	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}
	
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getJob_status() {
		return job_status;
	}
	public void setJob_status(String job_status) {
		this.job_status = job_status;
	}
	
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStorageName() {
		return storageName;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public int getPolicyId() {
        return policyId;
    }

	public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getInvalidAssetCount() {
        return invalidAssetCount;
    }
    
    public void setInvalidAssetCount(int invalidAssetCount) {
        this.invalidAssetCount = invalidAssetCount;
    }

}
