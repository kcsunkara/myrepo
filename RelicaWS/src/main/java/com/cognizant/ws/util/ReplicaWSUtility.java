package com.cognizant.ws.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReplicaWSUtility {

	private static final Logger LOG = LoggerFactory.getLogger(ReplicaWSUtility.class);
	
	@Value("${copypoolcount.sql}")
	private String copypoolcount;

	@Value("${eligiblecopyassets.sql}")
	private String eligiblecopyassets;

	@Value("${asset_source_dest_base_relativepaths.sql}")
	private String assetSourceDestinationPaths;

	@Value("${getPoolsizes.sql}")
	private String getPoolsizes;

	@Value("${getNewAssetsForMD5.sql}")
	private String getNewAssetsForMD5;

	@Value("${getStorageLocationId.sql}")
	private String getStorageLocationId;

	@Value("${getAssetsForMD5Olderthan90days.sql}")
	private String getAssetsForMD5Olderthan90days;

	@Value("${retreiveBatchJob.sql}")
	private String retreiveBatchJob;

	@Value("${getAbsoulteBasePath.sql}")
	private String getAbsoulteBasePath;

	@Value("${getRelativePath.sql}")
	private String getRelativePath;

	@Value("${updateErrors.sql}")
	private String updateErrors;

	@Value("${getAssetAbsoultePathforMD5.sql}")
	private String assetAbsoultePathforMD5;

	@Value("${insertBatchJobForMd5.sql}")
	private String insertBatchJobForMd5;

	@Value("${md5RecheckDuration}")
	private String md5RecheckDuration;

	@Value("${updateSuccess.sql}")
	private String updateSuccess;

	@Value("${assetInstanceId.sql}")
	private String assetInstanceId;

	@Value("${updateAssetsWithASSET_MD5.sql}")
	private String updateAssetsWithASSET_MD5;

	@Value("${updateAssetInstance.sql}")
	private String updateAssetInstance;

	@Value("${eligibleAassetsForDelete.sql}")
	private String findDeleteAssets;

	@Value("${storageLocationName.sql}")
	private String storageLocationName;

	@Value("${folderPath.sql}")
	private String folderPath;

	@Value("${updateDeletedAssetInstaces.sql}")
	private String updateDeletedAssetInstaces;

	@Value("${assetEntriesInHistory.sql}")
	private String assetEntriesInHistory;

	@Value("${updateAssetHistory.sql}")
	private String updateAssetHistory;

	@Value("${verifyPolicies.sql}")
	private String verifyPolicies;

	@Value("${assetsInstancesCount.sql}")
	private String assetsInstancesCount;

	@Value("${UpdatedAssetsInstances.sql}")
	private String UpdatedAssetsInstances;

	@Value("${changeInstanceStatus.sql}")
	private String changeInstanceStatus;

	@Value("${validAssetInstances.sql}")
	private String validAssetInstances;

	@Value("${primary.storage.id}")
	private String primaryStorageId;

	public String getPrimaryStorageId() {
		return primaryStorageId;
	}

	public void setPrimaryStorageId(String primaryStorageId) {
		this.primaryStorageId = primaryStorageId;
	}

	/**
	 * @return the validAssetInstances
	 */
	public String getValidAssetInstances() {
		return validAssetInstances;
	}

	/**
	 * @param validAssetInstances
	 *            the validAssetInstances to set
	 */
	public void setValidAssetInstances(String validAssetInstances) {
		this.validAssetInstances = validAssetInstances;
	}

	public String getAssetAbsoultePathforMD5() {
		return assetAbsoultePathforMD5;
	}

	public void setAssetAbsoultePathforMD5(String assetAbsoultePathforMD5) {
		this.assetAbsoultePathforMD5 = assetAbsoultePathforMD5;
	}

	public String getInsertBatchJobForMd5() {
		return insertBatchJobForMd5;
	}

	public void setInsertBatchJobForMd5(String insertBatchJobForMd5) {
		this.insertBatchJobForMd5 = insertBatchJobForMd5;
	}

	/**
	 * @return the changeInstanceStatus
	 */
	public String getChangeInstanceStatus() {
		return changeInstanceStatus;
	}

	/**
	 * @param changeInstanceStatus
	 *            the changeInstanceStatus to set
	 */
	public void setChangeInstanceStatus(String changeInstanceStatus) {
		this.changeInstanceStatus = changeInstanceStatus;
	}

	public String getUpdatedAssetsInstances() {
		return UpdatedAssetsInstances;
	}

	public void setUpdatedAssetsInstances(String updatedAssetsInstances) {
		UpdatedAssetsInstances = updatedAssetsInstances;
	}

	public String getAssetsInstancesCount() {
		return assetsInstancesCount;
	}

	public void setAssetsInstancesCount(String assetsInstancesCount) {
		this.assetsInstancesCount = assetsInstancesCount;
	}

	public String getVerifyPolicies() {
		return verifyPolicies;
	}

	public void setVerifyPolicies(String verifyPolicies) {
		this.verifyPolicies = verifyPolicies;
	}

	@Value("${tier1copies.sql}")
	private String tier1copies;

	public String getTier1copies() {
		return tier1copies;
	}

	public void setTier1copies(String tier1copies) {
		this.tier1copies = tier1copies;
	}

	@Value("${is_tier1storage_exists.sql}")
	private String isTier1StorageExists;

	@Value("${dbSnapshot.sql}")
	private String dbSnapshot;

	@Value("${updateAssetsWithDeleteDate.sql}")
	private String updateAssetsWithDeleteDate;

	@Value("${isPrimarySite.sql}")
	private String isPrimarySite;

	public String getIsPrimarySite() {
		return isPrimarySite;
	}

	public void setIsPrimarySite(String isPrimarySite) {
		this.isPrimarySite = isPrimarySite;
	}

	public String getUpdateAssetsWithDeleteDate() {
		return updateAssetsWithDeleteDate;
	}

	public void setUpdateAssetsWithDeleteDate(String updateAssetsWithDeleteDate) {
		this.updateAssetsWithDeleteDate = updateAssetsWithDeleteDate;
	}

	public String getDbSnapshot() {
		return dbSnapshot;
	}

	public void setDbSnapshot(String dbSnapshot) {
		this.dbSnapshot = dbSnapshot;
	}

	public String getUpdateAssetHistory() {
		return updateAssetHistory;
	}

	public void setUpdateAssetHistory(String updateAssetHistory) {
		this.updateAssetHistory = updateAssetHistory;
	}

	public String getAssetEntriesInHistory() {
		return assetEntriesInHistory;
	}

	public void setAssetEntriesInHistory(String assetEntriesInHistory) {
		this.assetEntriesInHistory = assetEntriesInHistory;
	}

	public String getUpdateDeletedAssetInstaces() {
		return updateDeletedAssetInstaces;
	}

	public void setUpdateDeletedAssetInstaces(String updateDeletedAssetInstaces) {
		this.updateDeletedAssetInstaces = updateDeletedAssetInstaces;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	/**
	 * @return the storageLocationName
	 */
	public String getStorageLocationName() {
		return storageLocationName;
	}

	/**
	 * @param storageLocationName
	 *            the storageLocationName to set
	 */
	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
	}

	public String getFindDeleteAssets() {
		return findDeleteAssets;
	}

	public void setFindDeleteAssets(String findDeleteAssets) {
		this.findDeleteAssets = findDeleteAssets;
	}

	public String getGetPoolsizes() {
		return getPoolsizes;
	}

	public void setGetPoolsizes(String getPoolsizes) {
		this.getPoolsizes = getPoolsizes;
	}

	public String getGetNewAssetsForMD5() {
		return getNewAssetsForMD5;
	}

	public void setGetNewAssetsForMD5(String getNewAssetsForMD5) {
		this.getNewAssetsForMD5 = getNewAssetsForMD5;
	}

	public String getGetStorageLocationId() {
		return getStorageLocationId;
	}

	public void setGetStorageLocationId(String getStorageLocationId) {
		this.getStorageLocationId = getStorageLocationId;
	}

	public String getGetAssetsForMD5Olderthan90days() {
		return getAssetsForMD5Olderthan90days;
	}

	public void setGetAssetsForMD5Olderthan90days(
			String getAssetsForMD5Olderthan90days) {
		this.getAssetsForMD5Olderthan90days = getAssetsForMD5Olderthan90days;
	}

	public String getGetAbsoulteBasePath() {
		return getAbsoulteBasePath;
	}

	public void setGetAbsoulteBasePath(String getAbsoulteBasePath) {
		this.getAbsoulteBasePath = getAbsoulteBasePath;
	}

	public String getGetRelativePath() {
		return getRelativePath;
	}

	public void setGetRelativePath(String getRelativePath) {
		this.getRelativePath = getRelativePath;
	}

	public String getUpdateErrors() {
		return updateErrors;
	}

	public void setUpdateErrors(String updateErrors) {
		this.updateErrors = updateErrors;
	}

	public String getUpdateSuccess() {
		return updateSuccess;
	}

	public void setUpdateSuccess(String updateSuccess) {
		this.updateSuccess = updateSuccess;
	}

	public String getAssetInstanceId() {
		return assetInstanceId;
	}

	public void setAssetInstanceId(String assetInstanceId) {
		this.assetInstanceId = assetInstanceId;
	}

	public String getUpdateAssetsWithASSET_MD5() {
		return updateAssetsWithASSET_MD5;
	}

	public void setUpdateAssetsWithASSET_MD5(String updateAssetsWithASSET_MD5) {
		this.updateAssetsWithASSET_MD5 = updateAssetsWithASSET_MD5;
	}

	public String getUpdateAssetInstance() {
		return updateAssetInstance;
	}

	public void setUpdateAssetInstance(String updateAssetInstance) {
		this.updateAssetInstance = updateAssetInstance;
	}

	public String getRetreiveBatchJob() {
		return retreiveBatchJob;
	}

	public void setRetreiveBatchJob(String retreiveBatchJob) {
		this.retreiveBatchJob = retreiveBatchJob;
	}

	public String getEligiblecopyassets() {
		return eligiblecopyassets;
	}

	public void setEligiblecopyassets(String eligiblecopyassets) {
		this.eligiblecopyassets = eligiblecopyassets;
	}

	public String getCopypoolcount() {
		return copypoolcount;
	}

	public void setCopypoolcount(String copypoolcount) {
		this.copypoolcount = copypoolcount;
	}

	public String getAssetSourceDestinationPaths() {
		return assetSourceDestinationPaths;
	}

	public void setAssetSourceDestinationPaths(
			String assetSourceDestinationPaths) {
		this.assetSourceDestinationPaths = assetSourceDestinationPaths;
	}

	public String getIsTier1StorageExists() {
		return isTier1StorageExists;
	}

	public void setIsTier1StorageExists(String isTier1StorageExists) {
		this.isTier1StorageExists = isTier1StorageExists;
	}

	public String getMd5RecheckDuration() {
		return md5RecheckDuration;
	}

	public void setMd5RecheckDuration(String md5RecheckDuration) {
		this.md5RecheckDuration = md5RecheckDuration;
	}

	@Value("${totalAssetsAndTotalSize.sql}")
	private String totalAssetsAndTotalSizeForCustomer;

	public String getTotalAssetsAndTotalSizeForCustomer() {
		return totalAssetsAndTotalSizeForCustomer;
	}

	public void setTotalAssetsAndTotalSizeForCustomer(
			String totalAssetsAndTotalSizeForCustomer) {
		this.totalAssetsAndTotalSizeForCustomer = totalAssetsAndTotalSizeForCustomer;
	}
	
	@Value("${customerUsageBeforeReportStartDate.sql}")
	private String customerUsageBeforeReportStartDate;
	
	public String getCustomerUsageBeforeReportStartDate() {
		return customerUsageBeforeReportStartDate;
	}

	public void setCustomerUsageBeforeReportStartDate(
			String customerUsageBeforeReportStartDate) {
		this.customerUsageBeforeReportStartDate = customerUsageBeforeReportStartDate;
	}

	@Value("${customerUsageInReportDateRange.sql}")
	private String customerUsageInReportDateRange;
	
	public String getCustomerUsageInReportDateRange() {
		return customerUsageInReportDateRange;
	}

	public void setCustomerUsageInReportDateRange(
			String customerUsageInReportDateRange) {
		this.customerUsageInReportDateRange = customerUsageInReportDateRange;
	}

	@Value("${allStorageNames.sql}")
	private String allStorageNames;

	public String getAllStorageNames() {
		return allStorageNames;
	}

	public void setAllStorageNames(String allStorageNames) {
		this.allStorageNames = allStorageNames;
	}
	
	@Value("${assetDetailsByJCID.sql}")
	private String assetDetailsByJCID;

	public String getAssetDetailsByJCID() {
		return assetDetailsByJCID;
	}

	public void setAssetDetailsByJCID(String assetDetailsByJCID) {
		this.assetDetailsByJCID = assetDetailsByJCID;
	}
	
	@Value("${inOutHostNameQuery.sql}")
	private String inOutHostNameQuery;

	public String getInOutHostNameQuery() {
		return inOutHostNameQuery;
	}

	public void setInOutHostNameQuery(String inOutHostNameQuery) {
		this.inOutHostNameQuery = inOutHostNameQuery;
	}
	

}
