package com.cognizant.ws.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ws.domain.BatchJob;
import com.cognizant.ws.domain.CustomCopyTier;
import com.cognizant.ws.domain.Md5HistoryRecs;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.exception.RequestMD5Exception;
import com.cognizant.ws.util.ReplicaWSConstants;
import com.cognizant.ws.util.ReplicaWSUtility;

@Repository
@Transactional
public class Md5ServiceImpl implements Md5Service {

	private static final Logger LOG = LoggerFactory
			.getLogger(Md5ServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ReplicaWSUtility replicaWSUtility;

	/**
	 * This method will get the request Md5 based on the storage id.
	 * 
	 * @param storageId
	 * @param domainName
	 * @return
	 * @throws RequestMD5Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, String> requestMD5(Long storageId, String hostName)
			throws RequestMD5Exception {
		LOG.info("Finding Asset that needs MD5 generation, on StorageId:",
				storageId);
		Long l_pool_max = 0L;
		Long s_pool_max = 0L;
		Long l_pool_curr = 0L;
		Long s_pool_curr = 0L;
		Map<String, String> assetInfo = new HashMap<String, String>();
		String absPath=null;
		CustomCopyTier cc=null;
		try {
			// validating the storage location id
			Query query = em.createNativeQuery(replicaWSUtility
					.getStorageLocationName());
			query.setParameter("storageId", storageId);
			query.unwrap(SQLQuery.class).addScalar("physical_name",
					StringType.INSTANCE);
			List<String> storageNameList = query.getResultList();
			if (storageNameList.isEmpty()) {
				LOG.info("Invalid StorageId:", storageId);
				// building the response asset info map
				buildAssetInfo(assetInfo, ReplicaWSConstants.ZERO,
						ReplicaWSConstants.EMPTY_STRING,
						ReplicaWSConstants.INVALID_STORAGE_ID + storageId);
				return assetInfo;
			}
		
			// RETRIEVING THE JOB POOL INFORMATION
			query = em.createNativeQuery(replicaWSUtility.getGetPoolsizes());
			query.setParameter("storagelocationId", storageId);
			List<Object[]> poolInfoList = query.getResultList();
			if (!poolInfoList.isEmpty()) {
				l_pool_max = Long.parseLong(poolInfoList.get(0)[1]
						+ ReplicaWSConstants.EMPTY_STRING);
				s_pool_max = Long.parseLong(poolInfoList.get(0)[2]
						+ ReplicaWSConstants.EMPTY_STRING);
				l_pool_curr = Long.parseLong(poolInfoList.get(0)[3]
						+ ReplicaWSConstants.EMPTY_STRING);
				s_pool_curr = Long.parseLong(poolInfoList.get(0)[4]
						+ ReplicaWSConstants.EMPTY_STRING);
				// CHECKING WHETHER BOTH JOB POOLS ARE FULL
				if ((l_pool_curr > l_pool_max) && (s_pool_curr > s_pool_max)) {
					LOG.info(
							"Both MD5_LARGE and MD5_SMALL pools are at maximum capacity for storageId:",
							storageId);
					// building the response asset info map
					buildAssetInfo(assetInfo, ReplicaWSConstants.ZERO,
							ReplicaWSConstants.EMPTY_STRING,
							ReplicaWSConstants.NO_CAPACITY_FOR_NEW_JOB
									+ storageId);
					return assetInfo;
				} else {
					// FIND THE ASSET INSTANCES WITH MD5 OLDER THAN 90 DAYS.
					LOG.info("Checking Assets for MD5 Older than 90days... ");
					List<Object[]> md5Assets = getMD5OlderThan90Days(storageId);
					if (md5Assets.isEmpty()) {
						LOG.info("No eligible assets are found for MD5 which are older than 90 days fro StorageId:"
								+ storageId);
						// IF NO ASSETS FOUND, IDENTIFYING THE ELIGIBLE ASSETS FOR MD5.
						md5Assets = getNewAssetsforMD5(storageId);
						
						if (md5Assets.isEmpty()) {
							LOG.info("No New Assets to perform MD5 for StorageIds:"+storageId);
							// building the response asset info map
							buildAssetInfo(
									assetInfo,
									ReplicaWSConstants.ZERO,
									ReplicaWSConstants.EMPTY_STRING,
									ReplicaWSConstants.NO_ASSETS_TO_PERFORM_MD5
											+ storageId);
							return assetInfo;
						}
					}
				}
			}
			LOG.info("Inserting a record into batchjobs table for MD5 for StorageID:"+storageId);
			cc = insertMD5BatchJob(storageId, hostName, new CustomCopyTier());
			
			if(cc.isAssetsFound())
			{

			query = em.createNativeQuery(replicaWSUtility.getAssetAbsoultePathforMD5());
			query.setParameter("storageId", storageId);
			query.setParameter("assetInstanceId", cc.getAssetInstanceId());

			query.unwrap(SQLQuery.class).addScalar("path", StringType.INSTANCE);

			absPath = (String) query.getSingleResult();
			LOG.info("Fetched the Absolute Path of a Asset to perform MD5 ::"+absPath+" for Asset Instance Id:"+cc.getAssetInstanceId());

			// building the response asset info map
			buildAssetInfo(assetInfo, cc.getJobId().toString(),
					absPath.toString(), ReplicaWSConstants.EMPTY_STRING);
			}
			else
			{
				buildAssetInfo(
						assetInfo,
						ReplicaWSConstants.ZERO,
						ReplicaWSConstants.EMPTY_STRING,
						ReplicaWSConstants.NO_ASSETS_TO_PERFORM_MD5
								+ storageId);
			}

		} catch (Exception e) {
			LOG.error("Exception in requestMD5::"+e.getMessage());
			if(cc.getJobId()!=null && cc.getJobId() > 0) 
			updateBatchJob(cc,"ERROR");
			throw new RequestMD5Exception(e.getMessage());
		}
		return assetInfo;
	}

	@Transactional(readOnly=true)
	private List<Object[]> getNewAssetsforMD5(Long storageId) {
		Query query;
		LOG.info("Fetching New Assets to perform MD5 for StorageId:"+storageId);
		List<Object[]> md5Assets;
		query = em.createNativeQuery(replicaWSUtility.getGetNewAssetsForMD5());
		query.setParameter("storagelocationId", storageId);

		md5Assets = query.getResultList();
		return md5Assets;
	}

	@Transactional(readOnly=true)
	private List<Object[]> getMD5OlderThan90Days(Long storageId) {
		LOG.info("Fetching Assets OLder than 90days to perform MD5 for StorageId:"+storageId);
		Query query;
		query = em.createNativeQuery(replicaWSUtility.getGetAssetsForMD5Olderthan90days());
		query.setParameter("md5RecheckDuration", Integer.parseInt(replicaWSUtility.getMd5RecheckDuration()));
		query.setParameter("storagelocationId", storageId);
		
		List<Object[]> md5Assets = query.getResultList();
		return md5Assets;
	}
	
	/**
	 * This method will insert the MD5 entry in batchjobs table.
	 * 
	 * @param storageId
	 * @param cc
	 * @param mrList
	 * @throws ReplicaWSException
	 * @throws SQLException 
	 * @throws RequestMD5Exception 
	 */
	@SuppressWarnings("deprecation")
	private CustomCopyTier insertMD5BatchJob(Long storageId, String hostName,CustomCopyTier cc) throws SQLException, RequestMD5Exception {
	
			LOG.info("Inserting the record into batchjobs table....");
			Long jobId=0L;
			Long assetInstanceId=0L;
			Session session =null;
			cc.setAssetsFound(false);
			 PreparedStatement pstmt = null;
			 Statement stmt = null;
             ResultSet rs=null;
             Connection conn=null;
		try {

			 		session = (Session)em.getDelegate();
			 		conn = session.connection();
				
					pstmt= conn.prepareStatement(replicaWSUtility.getInsertBatchJobForMd5());
					pstmt.setString(1, hostName.toString());
					pstmt.setLong(2, storageId);
					pstmt.setLong(3, storageId);
					pstmt.setLong(4, storageId);
					pstmt.setInt(5, Integer.parseInt(replicaWSUtility.getMd5RecheckDuration()));
				
					pstmt.executeUpdate();

                    rs = pstmt.getGeneratedKeys();

				if(rs.next())
				{
					jobId=rs.getLong(1);
					cc.setJobId(jobId);
					assetInstanceId=rs.getLong(2);
					cc.setAssetInstanceId(assetInstanceId);
					LOG.info("jobId:"+jobId+" assetInstanceId:"+assetInstanceId);
					cc.setAssetsFound(true);
				}
			
		
		} catch (Exception e) {
			LOG.error("exception in insertMD5BatchJob "+e.getMessage());
			throw new RequestMD5Exception(
					"exception in insertMD5BatchJob "+ e.getMessage());
		}
		finally
		{
			 pstmt.close();
      	     rs.close();
			 conn.close();
		}
		return cc;
	}
	/**
	 * This method builds the Asset Info
	 * @param assetInfo
	 * @param jobId
	 * @param absolutePath
	 * @param description
	 */
	private void buildAssetInfo(Map<String, String> assetInfo, String jobId,
			String absolutePath, String description) {
		assetInfo.put(ReplicaWSConstants.JOB_ID, jobId);
		assetInfo.put(ReplicaWSConstants.PATH, absolutePath);
		assetInfo.put(ReplicaWSConstants.DISCRIPTION, description);
	}
	/**
	 * This method updates the batchjob with final status
	 * @param cc
	 * @param status
	 * @throws RequestMD5Exception
	 */
	private void updateBatchJob(CustomCopyTier cc,String status) throws RequestMD5Exception {
		try {
			if("ERROR".equals(status))
			{
				LOG.info("Updating the status for batchjob with error status for job_id::"+cc.getJobId());
				BatchJob batchJobs = em.getReference(BatchJob.class, cc.getJobId());
				batchJobs.setJob_status("ERROR");
				batchJobs.setError_message("exception occured while calling AssetAbsoultePath.");
				batchJobs.setEnd_date(new Date());
				em.merge(batchJobs);
			}
	
		} catch (Exception e) {
			LOG.error("exception in updateBatchJob:"+e.getMessage());
			throw new RequestMD5Exception("exception in updateBatchJob:"
					+ e.getMessage());
		}
	}

	/**
	 * This method will get the request Md5 based on the storage name.
	 * 
	 * @param batch_id
	 * @param asset_instance_id
	 * @param error_message
	 * @return success or error message
	 */
	@SuppressWarnings("unchecked")
	public int responseMd5(Long batchId, String paramMd5, String errorCode)
			throws RequestMD5Exception {
		LOG.info(
				"Finding batch job that needs to be updated in batch job table:",
				batchId);
		int returnCode = 0;
		if (!errorCode.isEmpty()) {
			// update batch job with ERROR status and error code.
			Query query = em.createNativeQuery(replicaWSUtility.getUpdateErrors());
			query.setParameter("error_message", errorCode);
			query.setParameter("job_id", batchId);
			returnCode = query.executeUpdate();
		} else {
			try {
				// update batch job with success status
				Query query = em.createNativeQuery(replicaWSUtility
						.getUpdateSuccess());
				query.setParameter("job_id", batchId);
				query.executeUpdate();

				// getting the required parameters for the asset.
				Query instanceQuery = em.createNativeQuery(replicaWSUtility
						.getAssetInstanceId());
				instanceQuery.unwrap(SQLQuery.class)
						.addScalar("asset_id", LongType.INSTANCE)
						.addScalar("id", LongType.INSTANCE)
						.addScalar("user_md5", StringType.INSTANCE)
						.addScalar("asset_md5", StringType.INSTANCE);
				instanceQuery.setParameter("job_id", batchId);

				List<Object[]> md5details = instanceQuery.getResultList();
				if (!md5details.isEmpty()) {
					Object[] md5Detail = md5details.get(0);
					Long assetId = Long.parseLong(md5Detail[0]
							+ ReplicaWSConstants.EMPTY_STRING);
					Long assetInstanceId = Long.parseLong(md5Detail[1]
							+ ReplicaWSConstants.EMPTY_STRING);
					String USER_MD5 = (String) md5Detail[2];
					String ASSET_MD5 = (String) md5Detail[3];

					// prepare status to assign for the asset instance
					String status = null;
					String VALID_STATUS = "V";
					String IN_VALID_STATUS = "I";
					if (USER_MD5 != null) {
						if (paramMd5.equals(USER_MD5)) {
							status = VALID_STATUS;
						} else {
							status = IN_VALID_STATUS;
						}
					} else {
						if (ASSET_MD5 != null) {
							if (paramMd5.equals(ASSET_MD5)) {
								status = VALID_STATUS;
							} else {
								status = IN_VALID_STATUS;
							}
						} else {
							ASSET_MD5 = paramMd5;
							status = VALID_STATUS;
							// update asset
							Query updateAssetQuery = em
									.createNativeQuery(replicaWSUtility
											.getUpdateAssetsWithASSET_MD5());
							updateAssetQuery.setParameter("asset_md5", ASSET_MD5);
							updateAssetQuery.setParameter("assetId", assetId);
							returnCode = updateAssetQuery.executeUpdate();
						}
					}

					// update asset instance with status,location_md5 and last_check=current date
					Query updateInstanceQuery = em
							.createNativeQuery(replicaWSUtility
									.getUpdateAssetInstance());
					updateInstanceQuery.setParameter("status", status);
					updateInstanceQuery.setParameter("location_md5", paramMd5);
					updateInstanceQuery.setParameter("instanceId",
							assetInstanceId);
					returnCode = updateInstanceQuery.executeUpdate();

					// Add entry in MD-5 History Table
					Md5HistoryRecs md5HistoryRecs = new Md5HistoryRecs();
					md5HistoryRecs.setAsset_instance_id(assetInstanceId);
					md5HistoryRecs.setMd5(paramMd5);
					md5HistoryRecs.setCreate_date(new Date());
					em.persist(md5HistoryRecs);

				}
			} catch (Exception e) {
				LOG.error("exception in responseMd5:::"+e.getMessage());
				throw new RequestMD5Exception(e.getMessage());
			}

		}
		return returnCode;
	}

}
