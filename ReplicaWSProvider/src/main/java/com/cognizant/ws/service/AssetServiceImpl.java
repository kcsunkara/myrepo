package com.cognizant.ws.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.type.BooleanType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ws.domain.Asset;
import com.cognizant.ws.domain.AssetDTO;
import com.cognizant.ws.domain.AssetDTOList;
import com.cognizant.ws.domain.AssetInstancesDTO;
import com.cognizant.ws.domain.Policy;
import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.util.ReplicaWSUtility;

@Repository
@Transactional
public class AssetServiceImpl implements AssetService {
	private static final Logger LOG = LoggerFactory
			.getLogger(AssetServiceImpl.class);

	private static final int MAX_DELETE_COUNTER=900;
	private static final int MAX_BLOCK_SIZE=100000;
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	ReplicaWSUtility utility;
	
	/**
	 * This method will find the assets in database based on the assetId
	 */
    public Asset findByAssetId(Long id)
    {
		LOG.info("findById " + id);
		Asset asset = em.find(Asset.class, id);
		return asset;
	}
    /**
     * This method will find the asset in Database based on the policyId 
     */
	public Asset findByPolicyId(String policyId) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Asset> criteria = builder.createQuery(Asset.class);
		Root<Asset> member = criteria.from(Asset.class);
		criteria.select(member).where(
				builder.equal(member.get("policyId"), policyId));
		return em.createQuery(criteria).getSingleResult();
	}
		
	/**
	 * This method will fetch all the assets from database
	 */
	public List<Asset> getAll() {
		LOG.info("getAll");

		Query allAssets = em.createNamedQuery("allAssets");
		List<Asset> listAssets = allAssets.getResultList();

		LOG.info("getAll:" + listAssets);
		return listAssets;
	}

	/**
	 * This method will ingest the file asset to database
	 * @param assetDTOList
	 * @return
	 * @throws ReplicaWSException
	 */
	@Transactional
	public AssetDTOList saveFileAssets(AssetDTOList assetDTOList)
			throws ReplicaWSException {
		
		String inputJSON = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			inputJSON = mapper.writeValueAsString(assetDTOList);
			System.out.println(mapper.writeValueAsString(assetDTOList));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		String basePath = "";
		
		Long storageId = assetDTOList.getAssets().get(0).getAssetInstances().get(0).getStorageLocId();
		
		Query query = em.createNamedQuery("findBasePath");
		query.setParameter("storageLocationId", storageId);
		List<String> resultList = (List<String>) query.getResultList();
		if (!resultList.isEmpty()) {
			basePath = (String) resultList.get(0);
		} else {
			throw new ReplicaWSException("Storage Location ID Not Found. Invalid Storage Location ID as part of JSON Request. ");
		}

		for (AssetDTO assetDTO : assetDTOList.getAssets()) {
			LOG.info(" save Asset member............. " + assetDTO);
			String fsPath = assetDTO.getFs_path();
			if( storageId != Long.parseLong(utility.getLandingZoneId()) ) {
				if (fsPath.contains(basePath)) {
					fsPath = fsPath.substring(basePath.length(), fsPath.length());
					assetDTO.setFs_path(fsPath);
				} else {
					throw new ReplicaWSException("Service ran from invalid location ");
				}
			}
			// checking whether asset already exists
			assetExists(assetDTO);

			if (!assetDTO.isAssetExists()) {
				AssetDTO existingAssetDTO = getAsset(assetDTO);
				if (existingAssetDTO != null) {
					for (AssetInstancesDTO assetInstanceDTO : existingAssetDTO
							.getAssetInstances()) {
						assetInstanceDTO.setStatus("I");
					}
					// updating the existing asset instances with invalid status
					em.persist(existingAssetDTO);

					existingAssetDTO.setAsset_md5(null);
					existingAssetDTO.setFilesize(assetDTO.getFilesize());
					for (AssetInstancesDTO instanceDTO : assetDTO
							.getAssetInstances()) {
						instanceDTO.setAssetDTO(existingAssetDTO);
					}
					existingAssetDTO.setAssetInstances(assetDTO
							.getAssetInstances());
					// updating existing asset with new instances, file size and
					em.persist(existingAssetDTO);
				} else {
					// saving the new asset
					em.persist(assetDTO);
					LOG.info(" saveFileAssets...125.......... "
							+ assetDTO.getId());
				}
			}

		}
		System.out.println("exiting SAVE_ASSETS method...");
		return assetDTOList;
	}
	/**
	 * This method will update the asset in database
	 */
	@Transactional
	public Asset updateAsset(Asset member) {
		LOG.info(" edit Asset member............. " + member);
		em.merge(member);
		return member;
	}

	/**
	 * This will get the policy id based on the the asset's L-Path.
	 * 
	 * @param lPath
	 * @return PolicyId
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Long findAssetPolicy(String fsPath, List<Policy> policyList) {
		Long policyId = new Long(0);
		LOG.info("Finding the policy for the asset using the path............." + fsPath);
		Query query = null;
		if (!policyList.isEmpty()) {
			int maxLen = 0;
			for (Policy policy : policyList) {
				String pathInPolicy = policy.getFs_path();
				if (pathInPolicy != null && fsPath.contains(pathInPolicy)) {
					int len = StringUtils.countMatches(pathInPolicy, "/");
					if (len > maxLen) {
						maxLen = len;
						policyId = policy.getId();
					}
				}
			}
		}
		return policyId;
	}

	/**
	 * This method will update the asset with policy
	 * @param policyId
	 * @param assetId
	 * @return
	 */
	@Transactional
	public int updatePolicy(Long policyId, Long assetId) {
		LOG.info(policyId + " updatePolicy............. " + assetId);
		Query updatePolicyQuery = em.createNamedQuery("updatePolicy");
		
		Policy p = em.find(Policy.class, policyId);
		updatePolicyQuery.setParameter("policyObj", p);
		
//		updatePolicyQuery.setParameter("policyId", policyId);
		updatePolicyQuery.setParameter("assetId", assetId);
		int updated = updatePolicyQuery.executeUpdate();
		LOG.info(" updated............. " + updated);
		
		return updated;
	}
	
	 /**
	  * This method fetch asset details from database
	  * @param asset
	  * @return
	  */
	public AssetDTO getAsset(AssetDTO asset) {
		AssetDTO existingAsset = null;
		Query query = em.createNamedQuery("getAsset");
		query.setParameter("name", asset.getName());
		query.setParameter("filesize", asset.getFilesize());
		query.setParameter("fsPath", asset.getFs_path());
		List resultList = (List) query.getResultList();
		if (!resultList.isEmpty()) {
			existingAsset = (AssetDTO) resultList.get(0);
		}
		return existingAsset;
	}
	
	/**
	 * This method will check whether the asset is already available in database.
	 * 
	 * @param asset
	 */
	public void assetExists(AssetDTO asset) {
		boolean flag = false;
 		Query query = em.createNamedQuery("isAssetExists");
		query.setParameter("name", asset.getName());
		query.setParameter("filesize", asset.getFilesize());
		query.setParameter("fsPath", asset.getFs_path());
		List resultList = (List) query.getResultList();

		if (!resultList.isEmpty()) {
			asset.setId((Long)resultList.get(0));
			asset.setAssetExists(true);
		} 

}
	/**
	 * Returns the Delete eligible assets based on the storageId
	 * @param storageId
	 * @return
	 * @throws ReplicaWSException
	 */
	@Transactional
	public List<Map<String, String>> findDeleteAssets(Long storageId) throws ReplicaWSException{
		LOG.info(" findDeleteAssets  ............. " + storageId);
		List<Map<String, String>> markedAssets = new ArrayList<Map<String,String>>();
		Map<String, String> data = null;
		Query query = null;
		String storage_type=null;
	
		
		query = em.createNativeQuery(utility.getFindDeleteAssets());
		query.setParameter("storageId", storageId);
		query.unwrap(SQLQuery.class)
		.addScalar("ASSET_ID", LongType.INSTANCE)
		.addScalar("CUSTOMER_ID", LongType.INSTANCE)
		.addScalar("INSTANCE_ID", LongType.INSTANCE)
		.addScalar("ABSOULUTE_PATH", StringType.INSTANCE)
		.addScalar("storage_type", StringType.INSTANCE);
		List<Object[]> results = query.getResultList();
		
		LOG.info("ASSETS MARKED FOR DELETION:");

		for (Object[] objects : results) {
			Long assetId = (Long) objects[0];
			Long customerId = (Long) objects[1];
			Long instanceId = (Long) objects[2];
			String absPath = (String) objects[3];
			 storage_type = (String) objects[4];
			 String damInternalId = (String) objects[5];
			LOG.info("ASSET_ID: "+assetId+"; CUSTOMER_ID: "+customerId+"; ASSET_INSTANCE_ID: " + instanceId + "; ABSOLUTE_PATH: " + absPath);
			data = new HashMap<String,String>();
			data.put("assetId", assetId.toString());
			data.put("customerId", customerId.toString());
			data.put("instanceId", instanceId.toString());
			data.put("path",  absPath);
			data.put("storage_type",  storage_type);
			data.put("damInternalId", damInternalId);
			markedAssets.add(data);
		}
		
		// list2
		List<Map<String, String>> updatedAssets = new ArrayList<Map<String, String>>();
		query = em.createNativeQuery(utility.getUpdatedAssetsInstances());
		query.setParameter("storageId", storageId);
	
		query.unwrap(SQLQuery.class).addScalar("ASSET_ID", LongType.INSTANCE)
				.addScalar("CUSTOMER_ID", LongType.INSTANCE)
				.addScalar("INSTANCE_ID", LongType.INSTANCE)
				.addScalar("ABSOULUTE_PATH", StringType.INSTANCE)
				.addScalar("storage_type", StringType.INSTANCE); 
		List<Object[]> updatedAssetsResults = query.getResultList();

		if (!updatedAssetsResults.isEmpty()) {

			LOG.info("UPDATED ASSETS Instances MARKED FOR DELETION:");

			for (Object[] objects : updatedAssetsResults) {
				Long assetId = (Long) objects[0];
				Long customerId = (Long) objects[1];
				Long instanceId = (Long) objects[2];
				String absPath = (String) objects[3];
				storage_type = (String) objects[4];
				LOG.info("ASSET_ID: " + assetId + "; CUSTOMER_ID: "
						+ customerId + "; ASSET_INSTANCE_ID: " + instanceId
						+ "; ABSOLUTE_PATH: " + absPath+";");
				data = new HashMap<String, String>();
				data.put("assetId", assetId.toString());
				data.put("customerId", customerId.toString());
				data.put("instanceId", instanceId.toString());
				data.put("path", absPath);
				data.put("storage_type", storage_type);
				updatedAssets.add(data);
			}

			List<Map<String, String>> filteredUpdatedAssets = new ArrayList<Map<String, String>>();
			// filter
			for (Map<String, String> assetData : updatedAssets) {
				String assetId = assetData.get("assetId");
				query = em.createNativeQuery(utility.getAssetsInstancesCount());
				query.setParameter("assetId", assetId);
				List<Integer> countResults = query.getResultList();
				if (!countResults.isEmpty()) {
					Integer count = countResults.get(0);
					if (count >= 2) {
						filteredUpdatedAssets.add(assetData);
					}
				}
			}

			// final list
			markedAssets.addAll(filteredUpdatedAssets);
		}

		if (markedAssets.isEmpty()) {
			return null;
		}
	
		
		/* Marking the Instance status as 'W'. */
		List<Map<String, String>> markedAssets2 = new ArrayList<Map<String,String>>();
		
		for(Map<String,String> assetData: markedAssets){
			Asset asst= findByAssetId(Long.parseLong((String)assetData.get("assetId")));
			LOG.info(" Delete date in delete asset service:"+asst.getDelete_date());
			
			String instanceId = assetData.get("instanceId");
			String assetId = assetData.get("assetId");
			if (instanceId != null) {
				// change instance status to 'W' - work in progress
				query = em.createNativeQuery(utility.getChangeInstanceStatus());
				query.setParameter("instanceId", instanceId);
				query.setParameter("status", "W");
				query.executeUpdate();

				// check if there is a valid instance available or not
				query = em.createNativeQuery(utility.getValidAssetInstances());
				query.setParameter("assetId", assetId);
				List<Integer> validInstances = query.getResultList();
				Integer validInstancesCount = 0;
				if (!validInstances.isEmpty()) {
					validInstancesCount = validInstances.get(0);
				}

				if (validInstancesCount == 0) {
					// if no valid instance, reset the instance status to 'N'
					query = em.createNativeQuery(utility
							.getChangeInstanceStatus());
					query.setParameter("instanceId", instanceId);
					query.setParameter("status", "N");
					query.executeUpdate();
				} else {
					// if valid instance availble, add this instance data to
					// eligible deelte assets
					// list.
					markedAssets2.add(assetData);
				}
			}
		}
		
		return markedAssets2;
	}
	
	/**
	 * This method is used to update the status for the deleted asset instances.
	 * @param deletedInstanceIds
	 * @return
	 * @throws ReplicaWSException
	 */
	public int updateDeletedInstances(List<Integer> instanceIdsList)
			throws ReplicaWSException {
		LOG.info(" Calling updateDeletedInstances  ............. "	);
		
		LOG.info("getUpdateDeletedAssetInstaces():" 	+ utility.getUpdateDeletedAssetInstaces());
		Query query = em.createNativeQuery(utility.getUpdateDeletedAssetInstaces());
		query.setParameter("instanceIds", instanceIdsList);
		
		int updated = query.executeUpdate();
		
		return updated;
	}
	

	/**
	 * This method will update the delete date for the File assets.
	 * @param storageId
	 * @param fileSysSnapshot
	 * @return
	 * @throws ReplicaWSException
	 */
	public Map<String, String> deleteFilesystemCompare(Long storageId, String fileSysSnapshot) throws ReplicaWSException {
		
		LOG.info("FINDING, WHETHER THE SERVICE IS INVOKED FROM A NON-PRIMARY LOCATION, FOR STORAGE_ID: " + storageId);
		Map<String, String> returnVal = new HashMap<String, String>();
		Query findIsPrimary = em.createNativeQuery(utility.getIsPrimarySite());
		findIsPrimary.setParameter("storageId", storageId);
		findIsPrimary.unwrap(SQLQuery.class).addScalar("IS_PRIMARY", BooleanType.INSTANCE);
		Boolean isPrimary = (Boolean) findIsPrimary.getSingleResult();
	
		returnVal.put("isPrimary", isPrimary.toString());
		if(!isPrimary) {
			LOG.info("THE SERVICE IS INVOKED FROM A NON-PRIMARY LOCATION WITH STORAGE_ID: " + storageId);
			return returnVal;
		}
		
		LOG.info("PREPARING FILESYSTEM SNAPSHOT FROM THE LOG FILE: " + fileSysSnapshot);
		File logFile = new File(fileSysSnapshot);
		Map<String, Long> fileSysMap = new HashMap<String, Long>();
		Scanner scan = null;
		
		if(!logFile.exists() || !logFile.canRead()) {
			LOG.info("Can't read the file...");
			returnVal.put("updateCount", "0 - CANN'T READ THE FILE.");
			return returnVal;
		}
		try {
			scan = new Scanner(logFile, "UTF-8");
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				fileSysMap.put(line, null);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			throw new ReplicaWSException(
					"FILE NOT FOUND: FileSystem Snapshot log file could not be located...");
		}
		if(fileSysMap.size() == 0) {
			LOG.info("THERE ARE NO ENTRIES IN THE FILESYSTEM SNAPSHOT FILE...");
			returnVal.put("updateCount", "0 - THERE ARE NO ENTRIES IN THE FILESYSTEM SNAPSHOT FILE.");
			return returnVal;
		}
		
		LOG.info("PREPARING DB SNAPSHOT FILELIST FOR STORAGE_ID: " + storageId);
		Session session = (Session) em.getDelegate();
		SQLQuery query = session.createSQLQuery(utility.getDbSnapshot());
		query.setParameter("storageId", storageId);
		query.addScalar("ASSET_ID", LongType.INSTANCE);
		query.addScalar("PATH", StringType.INSTANCE);
		query.setFetchSize(Integer.valueOf(MAX_BLOCK_SIZE));
		query.setReadOnly(true);
		ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
		int updatedDeleteAssetsCount = 0;
		
		Map<String, Long> instanceInfo = new HashMap<String, Long>();
		
		int dbIterations = 0; 
		while (results.next()) {
			instanceInfo.put(results.getString(1), results.getLong(0));
			
			if (instanceInfo.size() % MAX_BLOCK_SIZE == 0) {
				LOG.info("DB Snapshot: " + dbIterations++);
				List<Long> deletedAssets = new ArrayList<Long>();
				for (Entry<String, Long> instance : instanceInfo.entrySet()) {
					if (!fileSysMap.containsKey(instance.getKey())) {
						LOG.info("DELETED FILE on FileSystem: "
								+ instance.getKey());
						deletedAssets.add(instance.getValue());
						if (deletedAssets.size() % MAX_DELETE_COUNTER == 0) {
							updatedDeleteAssetsCount += updateDeleteDate(deletedAssets);
							deletedAssets.removeAll(deletedAssets);
						}
					}
				}
				if (!deletedAssets.isEmpty()) {
					updatedDeleteAssetsCount += updateDeleteDate(deletedAssets);
				}
				instanceInfo.clear();
			} else if (instanceInfo.size() >0) {

				LOG.info("DB Snapshot: " + dbIterations++);
				List<Long> deletedAssets = new ArrayList<Long>();
				for (Entry<String, Long> instance : instanceInfo.entrySet()) {
					if (!fileSysMap.containsKey(instance.getKey())) {
						LOG.info("DELETED FILE on FileSystem: "
								+ instance.getKey());
						deletedAssets.add(instance.getValue());
						if (deletedAssets.size() % MAX_DELETE_COUNTER == 0) {
							updatedDeleteAssetsCount += updateDeleteDate(deletedAssets);
							deletedAssets.removeAll(deletedAssets);
						}
					}
				}
				if (!deletedAssets.isEmpty()) {
					updatedDeleteAssetsCount += updateDeleteDate(deletedAssets);
				}
				instanceInfo.clear();

			}
		}
		results.close();
		
		LOG.info("NO ASSETS FOUND TO UPDATE DELETE_DATE...");
		returnVal.put("updateCount", "" + updatedDeleteAssetsCount);
 		return returnVal;
	}
	
	/**
	 * This method updates List of ASSET IDs with delete date
	 * @param deletedAssets List of ASSET IDs to update the delete date
	 * @return Number of records that got updated
	 */

	private int updateDeleteDate(List<Long> deletedAssets) {
		int updateCount = 0;
		LOG.info("UPDATING " + deletedAssets.size() + "ASSETS DELETE_DATE TO CURRENT_TIMESTAMP...");
		Query updateAssetsQuery = em.createNativeQuery(utility.getUpdateAssetsWithDeleteDate());
		updateAssetsQuery.setParameter("assetIds", deletedAssets);
		updateCount = updateAssetsQuery.executeUpdate();
		
		LOG.info("UPDATING DAM_HOSTORY_RECS FOR " + deletedAssets.size() + "ASSETS THRU_DATE TO CURRENT_TIMESTAMP...");
		Query updateDamHistory = em.createNativeQuery(utility.getUpdateAssetHistory());
		updateDamHistory.setParameter("assetIds", deletedAssets);
		int historyUpdateCount = updateDamHistory.executeUpdate();
		LOG.info("DAM_HOSTORY_RECS UPDATED FOR " + historyUpdateCount + "ASSETS WITH THRU_DATE...");
		return updateCount;
	}

	/**
	 * This method is used to assets for verify policies.
	 * @return
	 * @throws ReplicaWSException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<MessageResponse> verifyPolicies() throws ReplicaWSException {

		MessageResponse messageResponse = null;

		List<MessageResponse> policyList = new ArrayList<MessageResponse>();

		Query query = em.createNativeQuery(utility.getVerifyPolicies());

		query.unwrap(SQLQuery.class)
				.addScalar("assetId", LongType.INSTANCE)
				.addScalar("fsPath", StringType.INSTANCE);

		List<Object[]> assetList = query.getResultList();

		if (assetList.isEmpty()) {
			messageResponse = new MessageResponse();
			messageResponse.setCode("200");
			messageResponse.setMessage("No eligible assets found");
			messageResponse.setDescription("No eligble assets found");
			policyList.add(messageResponse);

		} else {
			query = em.createNamedQuery("allPolicies");
			List<Policy> policiesList = (List<Policy>) query.getResultList();
			for (Object[] objects : assetList) {

				Long assetId = (Long) objects[0];
				String fsPath = (String) objects[1];

				Long verifyPolicyId = findAssetPolicy(fsPath,policiesList);
				LOG.info("verifyPolicyId.." + verifyPolicyId);

				if (verifyPolicyId != 0) {
					int updated = updatePolicy(verifyPolicyId, assetId);
					LOG.info("updated Policy.." + updated);
					if (updated != 0) {
						messageResponse = new MessageResponse();
						messageResponse.setCode("200");
						messageResponse.setMessage("updated the asset "
								+ assetId + " with policy " + verifyPolicyId);
						messageResponse.setDescription("updated the asset "
								+ assetId + " with policy " + verifyPolicyId);
						policyList.add(messageResponse);
					}
				} else {
					messageResponse = new MessageResponse();
					messageResponse.setCode("200");
					messageResponse
							.setMessage("No Policy match found for asset: "
									+ assetId);
					messageResponse
							.setDescription("No Policy match found for asset: "
									+ assetId);
					policyList.add(messageResponse);
				}

			}
		}

		return policyList;
	}
	
}
