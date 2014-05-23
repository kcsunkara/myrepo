package com.cognizant.ws.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ws.domain.Customer;
import com.cognizant.ws.domain.CustomerSummaryDTO;
import com.cognizant.ws.domain.CustomerUsageServiceParams;
import com.cognizant.ws.domain.Policy;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.pojo.Asset;
import com.cognizant.ws.pojo.AssetInstance;
import com.cognizant.ws.util.ReplicaWSConstants;
import com.cognizant.ws.util.ReplicaWSUtility;

@Repository
@Transactional
public class UtilityServiceImpl implements UtilityService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UtilityServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	ReplicaWSUtility utility;

	public Policy getPolicyDetails(Long policyId) {
		LOG.info("getPolicyDetails service invoked...");
		Policy policy = em.find(Policy.class, policyId);
		return policy;
	}

	public Customer getCustomerDetails(Long customerId) {
		LOG.info("getCustomerDetails service invoked...");
		Customer customer = em.find(Customer.class, customerId);
		return customer;
	}

	public CustomerSummaryDTO getCustomerSummary(Long customerId) {
		
		LOG.info("getCustomerSummary service invoked...");
		
		Customer customer = em.find(Customer.class, customerId);
		if(customer == null) {
			LOG.info("No Customer found with the CustomerId="+customerId);
			return null;
		}
		
		CustomerSummaryDTO summaryDTO = new CustomerSummaryDTO();
		
		Query query = em.createNativeQuery(utility.getTotalAssetsAndTotalSizeForCustomer());
		query.unwrap(SQLQuery.class)
		.addScalar("TOTALASSETS", LongType.INSTANCE)
		.addScalar("SIZEINBYTES", LongType.INSTANCE);
		query.setParameter("customerId", customerId);
		
		List<Object[]> resultObjList = query.getResultList();
		if(resultObjList == null) {
			LOG.info("No Assets found for the CustomerId="+customerId);
			summaryDTO.setTotalAssets(0L);
			summaryDTO.setTotalSizeOfAssets(0L);
			summaryDTO.setCustomer(customer);
			return summaryDTO;
		}
		
		summaryDTO.setTotalAssets((Long)resultObjList.get(0)[0]);
		summaryDTO.setTotalSizeOfAssets((Long)resultObjList.get(0)[1]);
		summaryDTO.setCustomer(customer);
		
		return summaryDTO;
	}

	public Asset getAssetDetailsByJCID(Long assetId) {
		
		LOG.info("getAssetDetailsByJCID service invoked...");
		
		/*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<AssetInstances> criteriaQuery = criteriaBuilder.createQuery(AssetInstances.class);
		Root<AssetInstances> member = criteriaQuery.from(AssetInstances.class);
		criteriaQuery.select(member).where(
				criteriaBuilder.equal(member.get("asset_id"),assetId), criteriaBuilder.notEqual(member.get("status"),"D"));
		List<AssetInstances> assetInstances = em.createQuery(criteriaQuery).getResultList();*/
		
		Query assetDetailsQuery = em.createNativeQuery(utility.getAssetDetailsByJCID());
		assetDetailsQuery.setParameter("assetId", assetId);
		return prepareAssetDetails(assetDetailsQuery);
	}
	
	public Asset getAssetDetailsByDAMID(String dam_internal_id, String repository) {
		
		LOG.info("getAssetDetailsByDAMID service invoked...");
		
		Query assetDetailsQuery = em.createNativeQuery(utility.getAssetDetailsByDAMID());
		assetDetailsQuery.setParameter("dam_internal_id", dam_internal_id);
		assetDetailsQuery.setParameter("repository", repository);
		return prepareAssetDetails(assetDetailsQuery);
	}
	
	private Asset prepareAssetDetails(Query assetDetailsQuery) {
		
		assetDetailsQuery.unwrap(SQLQuery.class)
		.addScalar("A_id", LongType.INSTANCE)
		.addScalar("name", StringType.INSTANCE)
		.addScalar("dam_internal_id", StringType.INSTANCE)
		.addScalar("repository", StringType.INSTANCE)
		.addScalar("fs_path", StringType.INSTANCE)
		.addScalar("filesize", LongType.INSTANCE)
		.addScalar("policy_id", LongType.INSTANCE)
		.addScalar("asset_md5", StringType.INSTANCE)
		.addScalar("user_md5", StringType.INSTANCE)
		.addScalar("created_date", DateType.INSTANCE)
		.addScalar("delete_date", DateType.INSTANCE)
		.addScalar("l_path", StringType.INSTANCE)
		.addScalar("customer_id", LongType.INSTANCE)
		.addScalar("customer_name", StringType.INSTANCE)
		.addScalar("AI_id", LongType.INSTANCE)
		.addScalar("storage_location_id", LongType.INSTANCE)
		.addScalar("filename", StringType.INSTANCE)
		.addScalar("location_md5", StringType.INSTANCE)
		.addScalar("create_date", DateType.INSTANCE)
		.addScalar("last_check", DateType.INSTANCE)
		.addScalar("purge_date", DateType.INSTANCE)
		.addScalar("status", StringType.INSTANCE)
		.addScalar("encrypted", StringType.INSTANCE)
		.addScalar("base_path", StringType.INSTANCE)
		.addScalar("signiant_id", StringType.INSTANCE);
		List<Object[]> resultObjList = assetDetailsQuery.getResultList();
		
		if(resultObjList==null || resultObjList.size()==0) {
			return null;
		}
		int itrCount=0;
		Asset asset = new Asset();
		asset.setAssetInstanceList(new ArrayList<AssetInstance>());
		for(Object[] record : resultObjList) {
			
			if(itrCount==0)  {
				asset.setId((Long)record[0]);
				asset.setName((String)record[1]);
				asset.setDam_internal_id((String)record[2]);
				asset.setRepository((String)record[3]);
				asset.setFs_path((String)record[4]);
				asset.setFilesize((Long)record[5]);
				asset.setPolicy_id((Long)record[6]);
				asset.setAsset_md5((String)record[7]);
				asset.setUser_md5((String)record[8]);
				asset.setCreated_date((Date)record[9]);
				asset.setDelete_date((Date)record[10]);
				asset.setL_path((String)record[11]);
				asset.setCustomer_id((Long)record[12]);
				asset.setCustomer_name((String)record[13]);
			}
			
			AssetInstance instance = new AssetInstance();
			
			instance.setId((Long)record[14]);
			instance.setAsset_id((Long)record[0]);
			instance.setStorageLocationId(((Long)record[15])+"");
			instance.setFilename((String)record[16]);
			instance.setLocation_md5((String)record[17]);
			instance.setCreate_date((Date)record[18]);
			instance.setLast_check((Date)record[19]);
			instance.setPurge_date((Date)record[20]);
			instance.setStatus((String)record[21]);
			instance.setEncrypted((String)record[22]);
			instance.setBase_path((String)record[23]);
			instance.setSigniant_id((String)record[24]);
			
			asset.getAssetInstanceList().add(instance);
			
			itrCount++;
		}
		return asset;
	}

	public Map<Date, Map<String, Long>> getCustomerUsage(CustomerUsageServiceParams params) throws ReplicaWSException {
		
		LOG.info("CustomerUsage service invoked...");
		Map<Date, Map<String, Long>> customerUsageMapBeforeStartDate = new TreeMap<Date, Map<String,Long>>();
		Map<String, Long> storageNameMap;
		try{
			/* Preparing the default storage name and size. */
			Query stNamesQuery = em.createNativeQuery(utility.getAllStorageNames());
			stNamesQuery.unwrap(SQLQuery.class)
			.addScalar("NAME", StringType.INSTANCE);
			List<String> storageNameList = stNamesQuery.getResultList();
			
			/* Collect the usage report, before report start date. */
			Query usageBeforeReportStartDateQuery = em.createNativeQuery(utility.getCustomerUsageBeforeReportStartDate());
			usageBeforeReportStartDateQuery.unwrap(SQLQuery.class)
			.addScalar("NAME", StringType.INSTANCE)
			.addScalar("SIZEINBYTES", LongType.INSTANCE);
			usageBeforeReportStartDateQuery.setParameter("startDate", params.getStartDate());
			usageBeforeReportStartDateQuery.setParameter("customerId", params.getCustomerId());
			List<Object[]> resultObjList1 = usageBeforeReportStartDateQuery.getResultList();
			
			if(resultObjList1 != null && resultObjList1.size() > 0) {
				storageNameMap = prepareDefaultStorageNameMap(storageNameList);
				for(Object[] record : resultObjList1) {
					String storageName = (String) record[0];
					Long size = (Long) record[1];
					storageNameMap.put(storageName, size);
				}
				/* Assign the usage report, for all the dates in the report date range. */
				Calendar start = Calendar.getInstance();
				start.setTime(params.getStartDate());
				Calendar end = Calendar.getInstance();
				end.setTime(params.getEndDate());
				for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
					customerUsageMapBeforeStartDate.put(date, prepareStorageNameMapClone(storageNameMap));
				}
			}
			
			/* Collect the usage report, before report start date. */
			Query usageInReportDateRangeQuery = em.createNativeQuery(utility.getCustomerUsageInReportDateRange());
			usageInReportDateRangeQuery.unwrap(SQLQuery.class)
			.addScalar("REPORTDATE", DateType.INSTANCE)
			.addScalar("STORAGENAME", StringType.INSTANCE)
			.addScalar("SIZEINBYTES", LongType.INSTANCE)
			.addScalar("FLAG", StringType.INSTANCE);
			usageInReportDateRangeQuery.setParameter("startDate", params.getStartDate());
			usageInReportDateRangeQuery.setParameter("endDate", params.getEndDate());
			usageInReportDateRangeQuery.setParameter("customerId", params.getCustomerId());
			List<Object[]> resultObjList2 = usageInReportDateRangeQuery.getResultList();
			
			for(Object[] record : resultObjList2) {
				Date reportDate = (Date) record[0];
				String storageName = (String) record[1];
				Long size = (Long) record[2];
				String addDeleteFlag = (String) record[3];
				Map<String, Long> reportMap = null;
				Long reportSize = null;
				if (customerUsageMapBeforeStartDate.containsKey(reportDate)) {
					reportMap = customerUsageMapBeforeStartDate.get(reportDate);
					reportSize = reportMap.containsKey(storageName) ? 
							ReplicaWSConstants.ADDED.equals(addDeleteFlag) ? reportMap.get(storageName)+ size 
									: reportMap.get(storageName) - size : size;
				} else {
					reportSize = size;
				}
				
				Calendar start = Calendar.getInstance();
				start.setTime(reportDate);
				Calendar end = Calendar.getInstance();
				end.setTime(params.getEndDate());
				if (ReplicaWSConstants.DELETED.equals(addDeleteFlag)) {
					start.add(Calendar.DATE, 1);
				}
				for (Date date = start.getTime(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
					Map<String, Long> currentMap = null;
					if(customerUsageMapBeforeStartDate.containsKey(date)) {
						currentMap = customerUsageMapBeforeStartDate.get(date);
					} else {
						currentMap = prepareDefaultStorageNameMap(storageNameList);
					}
					currentMap.put(storageName, reportSize);
					customerUsageMapBeforeStartDate.put(date, currentMap);
				}
			}
			
		}
		catch(Exception e) {
			LOG.debug("Exception in getCustomerUsage service ...");
			LOG.debug("Exception details"+e.getMessage());
			throw new ReplicaWSException(e.getMessage());
		}
		return customerUsageMapBeforeStartDate;
	}
	
	public Map<String, Long> prepareDefaultStorageNameMap(List<String> storageNameList) {
		Map<String, Long> storageNameMap = new TreeMap<String, Long>();
		for(String name : storageNameList) {
			storageNameMap.put(name, 0L);
		}
		return storageNameMap;
	}
	
	public Map<String, Long> prepareStorageNameMapClone(Map<String, Long> storageNameMapInput) {
		Map<String, Long> storageNameMapClone = new TreeMap<String, Long>();
		for(Entry<String, Long> entry : storageNameMapInput.entrySet()) {
			storageNameMapClone.put(entry.getKey(), entry.getValue());
		}
		return storageNameMapClone;
	}
	

}
