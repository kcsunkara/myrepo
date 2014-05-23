package com.cognizant.ws.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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

import com.cognizant.ws.domain.Asset;
import com.cognizant.ws.domain.AssetInstances;
import com.cognizant.ws.domain.BatchJob;
import com.cognizant.ws.domain.CustomCopyTier;
import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.util.ReplicaWSConstants;
import com.cognizant.ws.util.ReplicaWSUtility;

@Repository
@Transactional
public class CopyTierServiceImpl implements CopyTierService {

	private static final Logger LOG = LoggerFactory
			.getLogger(CopyTierServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ReplicaWSUtility utility;

	/**
	 * This request method will fetch all the CopyTier1 eligible assets
	 * from database based on the policy sites
	 * 
	 * @param storageId
	 * @return
	 * @throws ReplicaWSException
	 */
	@Transactional
	public List<MessageResponse> requestCopyTier(Long storageId,
			String inputHostName) throws ReplicaWSException {

		LinkedHashMap<String, String> response = null;
		List<MessageResponse> mrList = new ArrayList<MessageResponse>();
		CustomCopyTier cc = new CustomCopyTier();
		try {

			insertBatchJob(storageId, cc, mrList, inputHostName);

			if (cc.isAssetsFound()) {

				if (cc.getJobType() != null
						&& cc.getJobType().startsWith("COPY")) {
					// Fetching asset source and destination paths for copying
					// assets
					findAssetPaths(storageId, cc);

					MessageResponse mr = new MessageResponse();
					mr.setCode("200");
					mr.setMessage(cc.getJobId()
							+ " batch id successfully generated  for copy of AssetInstance:"
							+ cc.getAssetInstanceId());
					mr.setDescription(cc.getJobId()
							+ " batch id successfully generated  for copy of AssetInstance:"
							+ cc.getAssetInstanceId());
					mr.setHost(cc.getHostName());
					mr.setJobId(cc.getJobId().toString());
					mr.setSourcePath(cc.getSourcePath());
					mr.setDestinationPath(cc.getDestinationPath());
					mr.setCopyTierName("tier1");
					mr.setRequestState("");
					mr.setRequestNumber("");
					mrList.add(mr);

				}

			} else {
				LOG.info("Assets are not found to copy for this storage Id:",
						storageId);
				MessageResponse mr = new MessageResponse();
				mr.setCode("404");
				mr.setMessage("Assets are not found to copy for this storage Id:"
						+ storageId);
				mr.setDescription("Assets are not found to copy for this storage Id:"
						+ storageId);
				mrList.add(mr);
			}

		} catch (ReplicaWSException e) {
			LOG.error("exception in requestCopyTier:" + e.getMessage());
			if (cc.getJobId() != null && cc.getJobId() > 0)
				updateBatchJob(cc, mrList, "ERROR");

			throw new ReplicaWSException(e.getMessage(), e.response);
		} catch (Exception e) {
			LOG.error("exception in requestCopyTier:" + e.getMessage());
			if (cc.getJobId() != null && cc.getJobId() > 0)
				updateBatchJob(cc, mrList, "ERROR");
			throw new ReplicaWSException(e.getMessage(), e);
		}
		return mrList;
	}

	/**
	 * Fetching asset source and destination paths for copying assets from Database
	 * @param storageId
	 * @param cc
	 * @throws ReplicaWSException
	 */
	private void findAssetPaths(Long storageId, CustomCopyTier cc)
			throws ReplicaWSException {

		try {
			Query paths = em.createNativeQuery(utility
					.getAssetSourceDestinationPaths());
			paths.setParameter("storageId", storageId);
			//paths.setParameter("assetId", cc.getAssetId());
			paths.setParameter("assetInstanceId", cc.getAssetInstanceId());

			paths.unwrap(SQLQuery.class)
					.addScalar("SRC_BASEPATH_RELPATH", StringType.INSTANCE)
					.addScalar("DESTINATION_BASEPATH", StringType.INSTANCE)
					.addScalar("ASSET_INSTANCEID", LongType.INSTANCE)
					.addScalar("STORAGELOCATIONID", LongType.INSTANCE)
					.addScalar("PHYSICAL_NAME", StringType.INSTANCE);

			List<Object[]> pathsResults = paths.getResultList();

			for (Object[] objects : pathsResults) {

				if (objects != null && objects.length > 0) {

					cc.setSourcePath((String) objects[0]);
					cc.setDestinationPath((String) objects[1]);
					cc.setAssetInstanceId((Long) objects[2]);
					cc.setStorageLocationId((Long) objects[3]);
					cc.setHostName((String) objects[4]);

					LOG.info(" srcBase_RelPath:" + cc.getSourcePath() + " "
							+ "  destinationBasePath:"
							+ cc.getDestinationPath() + " "
							+ " assetInstanceId:" + cc.getAssetInstanceId()
							+ "  storageLocationId:"
							+ cc.getStorageLocationId() + "  hostName:"
							+ cc.getHostName());
				}
				break;
			}

		} catch (Exception e) {
			LOG.error("exception in findAssetPaths:"+e.getMessage());
			throw new ReplicaWSException("exception in findAssetPaths:", e);
		}

	}

	/**
	 * This method will insert the records into batchjobs table of database
	 * @param storageId
	 * @param cc
	 * @param mrList
	 * @throws ReplicaWSException
	 * @throws SQLException 
	 */
	private void insertBatchJob(Long storageId, CustomCopyTier cc,
			List<MessageResponse> mrList, String inputHostName) throws ReplicaWSException, SQLException {

		Long jobId=0L;
		Long assetInstanceId=0L;
		Session session =null;
		cc.setAssetsFound(false);
		 PreparedStatement pstmt = null;
         ResultSet rs=null;
         Connection conn=null;
	try {

		 	  session = (Session)em.getDelegate();
			 	
		 		conn = session.connection();
		 	 	
				pstmt= conn.prepareStatement(utility.getEligiblecopyassets());
				pstmt.setLong(1, storageId);
				pstmt.setString(2, inputHostName);
				pstmt.executeUpdate();

                rs = pstmt.getGeneratedKeys();
			

			if(rs.next())
			{
				jobId=rs.getLong(1);
				cc.setJobId(jobId);
				assetInstanceId=rs.getLong(2);
				cc.setAssetInstanceId(assetInstanceId);
				cc.setJobType(rs.getString(3));
				LOG.info("jobId:"+jobId+" assetInstanceId:"+assetInstanceId);
				cc.setAssetsFound(true);
			}
		
	
	} catch (Exception e) {
		LOG.error("exception in insertBatchJobs for copytier:"+e.getMessage());
		throw new ReplicaWSException(
				"exception in insertBatchJob", e);
	}
	finally
	{
		 pstmt.close();
  	     rs.close();
		 conn.close();
	}

	}

	
	/**
	 * This response method will update the copyTier status to
	 * database based on the policy sites
	 * 
	 * @param batchId
	 * @param status
	 * @return
	 * @throws ReplicaWSException
	 */
	@Transactional
	public MessageResponse responseCopyTier(Long batchId, String status)
			throws ReplicaWSException {
		LOG.info(status + " responseCopyTier........batchId..... " + batchId);
		CustomCopyTier cc = null;
		MessageResponse mr = null;
		try {

			BatchJob batchJobs = em.getReference(BatchJob.class, batchId);

			if (status.equals("success")) {

				cc = retrieveBatchJob(batchId);
				
				if(cc == null) {
					LOG.error("Exception scenario in responseCopyTier, JOB_STATUS changed by other process for JOB_ID = " + batchId);
					throw new ReplicaWSException("JOB_STATUS changed by other process for JOB_ID = " + batchId);
				}

				AssetInstances ai = em.getReference(AssetInstances.class, cc.getAssetInstanceId());

				LOG.info("In responseCopyTier for AssetId:" + ai.getAsset().getId());

				Asset asset = new Asset();
				asset.setId(ai.getAsset().getId());

				AssetInstances aii = new AssetInstances();

				aii.setStorageLocationId(cc.getStorageLocationId().toString());
				aii.setFilename(cc.getFileName());
				aii.setAsset(asset);
				aii.setStatus(ReplicaWSConstants.AI_STATUS_NEW);
				aii.setCreate_date(new Date());
				em.persist(aii);

				batchJobs.setEnd_date(new Date());
				batchJobs.setJob_status(ReplicaWSConstants.COMPLETED);
				em.merge(batchJobs);

				// setting response
				mr = new MessageResponse();
				mr.setCode("200");
				mr.setMessage(status);
				mr.setDescription("updated the jobId:" + batchId + " with "
						+ status);

			} else {
				batchJobs.setError_message("ERROR");
				batchJobs.setEnd_date(new Date());
				batchJobs.setJob_status("ERROR");
				em.merge(batchJobs);

				mr = new MessageResponse();
				mr.setCode("500");
				mr.setMessage(status);
				mr.setDescription("updated the jobId:" + batchId + " with "
						+ status);
			}

		} catch (Exception e) {
			LOG.error("exception in responseCopyTier:"+e.getMessage());
			throw new ReplicaWSException("exception in responseCopyTier", e);
		}

		return mr;

	}
	/**
	 * This method will retrieve the batchjob record from Database
	 * @param batchId
	 * @return
	 * @throws ReplicaWSException
	 */
	private CustomCopyTier retrieveBatchJob(Long batchId) throws ReplicaWSException {

		CustomCopyTier cc = null;

		try {
			Query query = em.createNativeQuery(utility.getRetreiveBatchJob());
			query.setParameter("batchId", batchId);
			query.unwrap(SQLQuery.class).addScalar("AID", LongType.INSTANCE)
					.addScalar("NAME", StringType.INSTANCE)
					.addScalar("storageId", LongType.INSTANCE);

			List<Object[]> batchResults = query.getResultList();

			for (Object[] objects : batchResults) {
				if (objects != null && objects.length > 0) {
					cc = new CustomCopyTier();
					cc.setAssetInstanceId((Long) objects[0]);
					String sourcePath = (String) objects[1];
					String fileName = sourcePath.substring(
							sourcePath.lastIndexOf("/") + 1,
							sourcePath.length());
					cc.setFileName(fileName);
					cc.setStorageLocationId((Long) objects[2]);

					LOG.info(" In retrieveBatchJob assetInstanceId:"
							+ cc.getAssetInstanceId() + "  file name:"
							+ cc.getFileName() + "  storageLocationId:"
							+ cc.getStorageLocationId());

				}
				break;
			}
		} catch (Exception e) {
			LOG.error("Exception in retrieveBatchJob for copy tier :"+e.getMessage());
			throw new ReplicaWSException("Exception in retrieveBatchJob:", e);
		}
		return cc;
	}

	
	private void updateBatchJob(CustomCopyTier cc,
			List<MessageResponse> mrList,String status) throws ReplicaWSException {
		
		try {
			BatchJob batchJobs = em.getReference(BatchJob.class, cc.getJobId());
			
			if(!"ERROR".equals(status))
			{
			    batchJobs.setJob_detail(cc.getRequestNumber());
			}
			else
			{
				batchJobs.setJob_status("ERROR");
				batchJobs.setError_message("exception occured");
				batchJobs.setEnd_date(new Date());
			}
			em.merge(batchJobs);
		
			MessageResponse mr = new MessageResponse();
			mr.setCode("200");
			mr.setMessage((cc.getRequestNumber() != null ? cc.getRequestNumber() :"")
					+ "  will be processed in the next iteration for job " + cc.getJobId());
			mr.setDescription((cc.getRequestNumber() != null ? cc.getRequestNumber() :"")
					+ "  will be processed in the next iteration for job " + cc.getJobId());
			mrList.add(mr);
			LOG.info("end of updationg batch job for JOB_ID: "+cc.getJobId());
			

		} catch (Exception e) {
			LOG.error("exception in updateBatchJob:"
					+e.getMessage());
			throw new ReplicaWSException("exception in updateBatchJob:"
					+ e.getMessage());
		}
	}

	/**
	 * This method with find and replace the actual source hostname with the alternate hostname 
	 * to route copy request. 
	 * @param messageResponseList
	 * @param inHostName Host name that invoked copy service
	 * @return messageResponseList MessageResopnse list with hostname replaced with alternate hostname 
	 * @throws ReplicaWSException
	 */
	public List<MessageResponse> getAlernateHost(List<MessageResponse> messageResponseList, String inputHostName) throws ReplicaWSException {
		
		LOG.info("In getAlernateHost servicve - Finding alternate source hostname to route the COPY request...");
		for(MessageResponse msg : messageResponseList) {
			
			if(msg.getHost()==null || msg.getHost().isEmpty()) {
				continue;
			}
			
			Query inOutHostNamesQuery = em.createNativeQuery(utility.getInOutHostNameQuery());
			inOutHostNamesQuery.unwrap(SQLQuery.class).addScalar("OUTPUT_HOSTNAME", StringType.INSTANCE);
			inOutHostNamesQuery.setParameter("inputHostName", inputHostName);
			inOutHostNamesQuery.setParameter("sourceHostName", msg.getHost());
			String alternateHostName = (String) inOutHostNamesQuery.getSingleResult();
			
			if(! ReplicaWSConstants.NO_HOST_MAPPING_FOUND.equals(alternateHostName) ) {
				LOG.info("Changing the source host to alternate host : " + msg.getHost() + " -to- " + alternateHostName);
				msg.setHost(alternateHostName);
			}
			
		}
		return messageResponseList;
	}
	
}
