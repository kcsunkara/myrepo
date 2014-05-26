package com.cognizant.ws.service;

import java.util.List;
import java.util.Map;

import com.cognizant.ws.domain.Asset;
import com.cognizant.ws.domain.AssetDTOList;
import com.cognizant.ws.domain.Policy;
import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.exception.ReplicaWSException;

public interface AssetService {
	/**
	 * Returns the Asset identified by the id
	 * 
	 * @param id
	 * @return the Asset
	 * @throws MessageResponse
	 *             if no asset is found with specified id
	 */
	public Asset findByAssetId(Long id);

	/**
	 * This method will find the policy based on Id.
	 * @param policyId
	 * @return
	 */
	public Asset findByPolicyId(String policyId);

	public List<Asset> getAll();
	
	/**
	 * This method will ingest the file asset to database
	 * @param assetDTOList
	 * @return
	 * @throws ReplicaWSException
	 */
	public AssetDTOList saveFileAssets(AssetDTOList assetDTOList)
			throws ReplicaWSException;
	
	/**
	 * This method will update the asset data in database
	 * @param person
	 * @return
	 */
	public Asset updateAsset(Asset person);

	/**
	 * This method will update the asset with policy
	 * @param policyId
	 * @param assetId
	 * @return
	 */
	public int updatePolicy(Long policyId, Long assetId);

	/**
	 * This will get the policy id based on the the asset's fsPath.
	 * 
	 * @param fsPath
	 * @return PolicyId
	 */
	public Long findAssetPolicy(String fsPath,List<Policy> policyList);

	/**
	 * Returns the Delete eligible assets based on the storageId
	 * @param storageId
	 * @return
	 * @throws ReplicaWSException
	 */
	public List<Map<String, String>> findDeleteAssets(Long storageId) throws ReplicaWSException;
	
	/**
	 * This method is used to update the status for the deleted asset instances.
	 * @param deletedInstanceIds
	 * @return
	 * @throws ReplicaWSException
	 */
	public int updateDeletedInstances(List<Integer> instanceIdsList)throws ReplicaWSException;
	
	/**
	 * This method will update the delete date for the File assets.
	 * @param storageId
	 * @param fileSysSnapshot
	 * @return
	 * @throws ReplicaWSException
	 */
	public Map<String, String> deleteFilesystemCompare(Long storageId, String fileSysSnapshot) throws ReplicaWSException;
	
	/**
	 * This method is used to assets for verify policies.
	 * @return
	 * @throws ReplicaWSException
	 */
	public List<MessageResponse> verifyPolicies()throws ReplicaWSException;
	
}
