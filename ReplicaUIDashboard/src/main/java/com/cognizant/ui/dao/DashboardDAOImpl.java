package com.cognizant.ui.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.hibernate.SQLQuery;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.ui.beans.CustomPolicySiteInfo;
import com.cognizant.ui.beans.SearchCriteria;
import com.cognizant.ui.exception.MessageResponse;
import com.cognizant.ui.json.AssetDTO;
import com.cognizant.ui.json.AssetDTOList;
import com.cognizant.ui.json.AssetInstancesDTO;
import com.cognizant.ui.json.Customer;
import com.cognizant.ui.model.Asset;
import com.cognizant.ui.model.AssetDetails;
import com.cognizant.ui.model.CustomJobBean;
import com.cognizant.ui.model.Policy;
import com.cognizant.ui.util.ReplicaUIUtility;

@Repository
@Transactional
public class DashboardDAOImpl implements DashboardDAO {

    private static final Logger LOG = Logger.getLogger(DashboardDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ReplicaUIUtility util;

    /**
     * This method will fetch the asset details from database based
     * on the search criteria.
     * 
     * @param criteria
     * @return list of assets
     */
    public List getAssets(SearchCriteria criteria) {

        LOG.info("getAssets.." + criteria);

        // Session session = getCurrentSession();

        StringBuilder sql = new StringBuilder(
                "select name,id,fs_path,filesize,asset_md5,policy_id,created_date,delete_date from "
                        + " ASSET_REPLICA.dbo.Assets a WITH(NOLOCK) where 1=1 ");

        LOG.info("getAssets.." + sql);

        if (criteria.getAssetId() != null) {
            sql.append(" and a.id=").append(criteria.getAssetId());
        }

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            String assetName = criteria.getName();
            boolean escape = false;
            if (assetName.contains("'")) {
                assetName = assetName.replace("'", "''");
            }
            if (assetName.contains("[")) {
                assetName = assetName.replace("[", "/[");
                escape = true;
            }

            // sql.append(" and (a.name like N'%").append(assetName).append("%' or a.name='"+assetName+"')");
            sql.append(" and (a.name like N'%").append(assetName).append("%' ");
            if (escape) {
                sql.append("ESCAPE '/'");
            } else {
                sql.append(" or a.name='" + assetName + "' ");
            }
            sql.append(")");
        }

        if (criteria.getPath() != null && !criteria.getPath().isEmpty()) {
            String fsPath = criteria.getPath();
            boolean escape = false;
            if (fsPath.contains("'")) {
            	fsPath = fsPath.replace("'", "''");
            }
            if (fsPath.contains("[")) {
            	fsPath = fsPath.replace("[", "/[");
                escape = true;
            }
            // sql.append(" and (a.l_Path like N'%").append(lPath).append("%' or a.l_Path='"+lPath+"')");
            sql.append(" and (a.l_Path like N'%").append(fsPath).append("%' ");
            if (escape) {
                sql.append("ESCAPE '/'");
            } else {
                sql.append(" or a.l_Path='" + fsPath + "' ");
            }
            sql.append(")");
        }

        Query query = em.createNativeQuery(sql.toString());

        query.unwrap(SQLQuery.class).addScalar("name", StringType.INSTANCE)
                .addScalar("id", LongType.INSTANCE)
                .addScalar("fs_path", StringType.INSTANCE)
                .addScalar("filesize", LongType.INSTANCE)
                .addScalar("asset_md5", StringType.INSTANCE)
                .addScalar("policy_id", IntegerType.INSTANCE)
                .addScalar("created_date", TimestampType.INSTANCE)
                .addScalar("delete_date", TimestampType.INSTANCE);

        List<Object[]> resList = query.getResultList();
        List<Asset> assetList = new ArrayList<Asset>();

        for (Object[] objects : resList) {
            Asset a = new Asset();
            a.setName((String) objects[0]);
            a.setId((Long) objects[1]);
            a.setFsPath((String) objects[2]);
            a.setFilesize((Long) objects[3]);
            a.setAssetMD5((String) objects[4]);
            if (objects[5] != null)
                a.setPolicyId((Integer) objects[5]);

            a.setCreatedDate((Date) objects[6]);
            a.setDeleteDate((Date) objects[7]);
            if (a.getPolicyId() != 0) {
                String custmerQueryString = "select c.name from Customer c , Policy p where"
                        + " p.customerId = c.id and p.id = " + a.getPolicyId();
                Query custmerQuery = em.createQuery(custmerQueryString);
                List<String> custId = custmerQuery.getResultList();

                LOG.info("Customer id when policyid is not null:" + custId);

                if (custId.isEmpty())
                    a.setCustomerName("");
                else
                    a.setCustomerName(custId.get(0));
            }
            assetList.add(a);
        }

        return assetList;
    }

    /**
     * This method will fetch the asset and asset instances details for
     * the selected asset in asset search page.
     * 
     * @param criteria
     * @retun list of asset details.
     */

    public List getAssetDetails(SearchCriteria criteria) {

        LOG.info("getAssetDetails.." + criteria);

        // Session session = getCurrentSession();

        StringBuilder sql = new StringBuilder(
                "select a.id as id, a.name as name, "
                        + "a.policyId as policyId, a.createdDate as createdDate, a.deleteDate as deleteDate, "
                        + " a.filesize as filesize, a.assetMD5 as assetMD5, a.userMD5 as userMD5,"
                        + "i.storageLocationId as storageLocationId, "
                        + "a.fsPath as fsPath, i.filename as filename, i.locationMD5 as locationMD5, i.createDate as createDate,"
                        + " i.lastCheck as lastCheck, "
                        + "i.purgeDate as purgeDate, i.encrypted as encrypted, d.value as value, sl.name as slname from "
                        + "Asset a, AssetInstance i, StorageLocation sl, Dictionary d "
                        + "where sl.id=i.storageLocationId and d.keyname=i.status and a.id=i.assetId ");

        LOG.info("getAssetDetails.." + sql);

        if (criteria.getAssetId() != null) {
            sql.append(" and a.id=").append(criteria.getAssetId());
        }

        Query query = em.createQuery(sql.toString());

        // query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Object[]> resultList = query.getResultList();
        List<AssetDetails> assetList = new ArrayList<AssetDetails>();

        for (Object[] objects : resultList) {
            AssetDetails a = new AssetDetails();
            a.setId((Long) objects[0]);
            a.setName((String) objects[1]);
            if (objects[2] != null) {
                a.setPolicyId((Integer) objects[2]);
            }
            a.setCreatedDate((Date) objects[3]);
            a.setDeleteDate((Date) objects[4]);
            a.setFilesize((Long) objects[5]);
            a.setAssetMD5((String) objects[6]);
            a.setUserMD5((String) objects[7]);
            a.setStorageLocationId((Integer) objects[8]);
            a.setFsPath((String) objects[9]);
            a.setFilename((String) objects[10]);
            a.setLocationMD5((String) objects[11]);
            a.setCreateDate((Date) objects[12]);
            a.setLastCheck((Date) objects[13]);
            a.setPurgeDate((Date) objects[14]);
            a.setEncrypted((Boolean) objects[15]);
            a.setValue((String) objects[16]);
            a.setStorageLocationName((String) objects[17]);
            
            if (a.getPolicyId() != 0) {
                String custmerQueryString = "select c.name from Customer c , Policy p where"
                        + " p.customerId = c.id and p.id = " + a.getPolicyId();
                Query custmerQuery = em.createQuery(custmerQueryString);
                List<String> custId = custmerQuery.getResultList();

                LOG.info("Customer id when policyid is not null:" + custId);

                if (custId.isEmpty())
                    a.setCustomerName("");
                else
                    a.setCustomerName(custId.get(0));
            }
            assetList.add(a);
        }
        return assetList;
    }

    /**
     * This method will fetch the counts of the jobs from batchjobs
     * table as per the status.
     * 
     * @param modelMap
     * @param jobDate
     * @return
     */
    public CustomJobBean getJobCountForAllJobTypes(String jobDate) {
        LOG.info(" getGetJobCountForAllJobTypes.... ");
        CustomJobBean cb = null;

        Query query = em.createNativeQuery(util.getJobCountForAllJobTypes());

        if (jobDate == null || jobDate.equals(""))
            jobDate = "1";

        query.setParameter("day", -Integer.parseInt(jobDate));

        List<Object[]> resultList = query.getResultList();

        for (Object[] objects : resultList) {
            cb = new CustomJobBean();

            cb.setMD5RunningCount(objects[0] == null ? 0 : (Integer) objects[0]);
            cb.setMD5ErrorCount(objects[1] == null ? 0 : (Integer) objects[1]);
            cb.setMD5CompletedCount(objects[2] == null ? 0 : (Integer) objects[2]);
            cb.setCopyTier1CompletedCount(objects[3] == null ? 0 : (Integer) objects[3]);
            cb.setCopyTier1RunningCount(objects[4] == null ? 0 : (Integer) objects[4]);
            cb.setCopyTier1ErrorCount(objects[5] == null ? 0 : (Integer) objects[5]);
            cb.setMd5Total(objects[6] == null ? 0 : (Integer) objects[6]);
            cb.setCopytier1Total(objects[7] == null ? 0 : (Integer) objects[7]);
            cb.setTotalRunning(objects[8] == null ? 0 : (Integer) objects[8]);
            cb.setTotalFailed(objects[9] == null ? 0 : (Integer) objects[9]);
            cb.setTotalCompleted(objects[10] == null ? 0 : (Integer) objects[10]);
            
            cb.setFinalTotal(cb.getTotalRunning() + cb.getTotalFailed() + cb.getTotalCompleted());
        }
        
        Query query1 = em.createNativeQuery(util.getInvalidAssetCount());
        List<Integer> resultList1 = query1.getResultList();
        if (resultList1.isEmpty())
            cb.setInvalidAssetCount(0);
        else
            cb.setInvalidAssetCount(resultList1.get(0));

        return cb;
    }

    /**
     * This method will fetch the details of jobs based on the jobType
     * selected.
     * 
     * @param jobType
     * @param day
     * @param jobStatus
     * @return
     */
    public List<CustomJobBean> getJobsByType(String jobType, int day,
            String jobStatus) {
        LOG.info(" getJobsByType.... ");
        String jobType1 = null, jobType2 = null, jobType3 = null;
        String jobDate = null;
        Query query = null;

        if (jobStatus.equals("new"))
            query = em.createNativeQuery(util.getAllNewJobsByType());
        else
            query = em.createNativeQuery(util.getAllJobsByType());

        query.unwrap(SQLQuery.class).addScalar("JOB_ID", LongType.INSTANCE)
                .addScalar("JOB_TYPE", StringType.INSTANCE)
                .addScalar("FILENAME", StringType.INSTANCE)
                .addScalar("STORAGE_NAME", StringType.INSTANCE)
                .addScalar("REQUEST_HOST_NAME", StringType.INSTANCE)
                .addScalar("START_DATE", TimestampType.INSTANCE)
                .addScalar("END_DATE", TimestampType.INSTANCE)
                .addScalar("ASSETID", LongType.INSTANCE)
                .addScalar("ERRMSG", StringType.INSTANCE)
                .addScalar("JOB_STATUS", StringType.INSTANCE)
                .addScalar("POLICY_ID", IntegerType.INSTANCE)
                .addScalar("CUST_NAME", StringType.INSTANCE);

        if (jobType.equals("md5")) {
            jobType1 = "MD5_LARGE";
            jobType2 = "MD5_SMALL";
            jobType3 = "";
        } else if (jobType.equals("copytier1")) {
            jobType1 = "COPY_LARGE";
            jobType2 = "COPY_SMALL";
            jobType3 = "";
        } 
        query.setParameter("jobType1", jobType1);
        query.setParameter("jobType2", jobType2);
        query.setParameter("jobType3", jobType3);

        if (!jobStatus.equals("new"))
            query.setParameter("jobStatus", jobStatus.toUpperCase());

        query.setParameter("day", day);

        List<CustomJobBean> assetList = new ArrayList<CustomJobBean>();
        List<Object[]> resultList = query.getResultList();
        DateFormat dtFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        for (Object[] objects : resultList) {
            CustomJobBean a = new CustomJobBean();
            a.setJobId((Long) objects[0]);
            a.setJob_type((String) objects[1]);
            a.setFileName((String) objects[2]);
            a.setStorageName((String) objects[3]);
            String hostName = (String) objects[4];
            if (hostName == null) {
                a.setRequestHostName(" ");
            } else {
                a.setRequestHostName(hostName);
            }
            Timestamp st = (Timestamp) objects[5];
            java.util.Date stDt = new java.util.Date(st.getTime());
            String strDate = dtFormatter.format(stDt);
            a.setStart_date(strDate);

            if (jobStatus.equals("new")) {
                a.setEnd_date(null);
            } else {
                st = (Timestamp) objects[6];
                if(st != null) {
                	java.util.Date endDt = new java.util.Date(st.getTime());
                	String endDate = dtFormatter.format(endDt);
                	a.setEnd_date(endDate);
                } else {
                	a.setEnd_date("");
                }
                
            }
            a.setAssetId((Long) objects[7]);
            String errMsg = (String) objects[8];
            if (errMsg == null) {
                a.setCode(" ");
            } else {
                a.setCode(errMsg);
            }
            a.setJob_status((String) objects[9]);
            a.setPolicyId(objects[10] == null? 0:(Integer) objects[10]);
            String name=(String) objects[11];
            if(name == null)
            {
                a.setName(" ");
            }else{
                a.setName(name);
            }
           // a.setName(objects[11] == null? "":);
            assetList.add(a);
        }
        // assetList.add(cb1);
        return assetList;
    }

    public List<Customer> getCustomerNames() {
    
        Customer customer = null;
        List<Customer> custList = new ArrayList<Customer>();
        
        Query query = em.createNativeQuery(util.getCustomeNames());
        query.unwrap(SQLQuery.class).addScalar("id", LongType.INSTANCE).addScalar("name", StringType.INSTANCE);
        
        List<Object[]> resList = query.getResultList();
        for (Object[] objects : resList) {
            customer = new Customer();
            customer.setId((Long) objects[0]);
            customer.setName((String) objects[1]);
            custList.add(customer);
        }
        
        return custList;
        
    }


    public CustomJobBean getJobDetails(Long jobId) {
        
        CustomJobBean customBean = null;
        Query query = em.createNativeQuery(util.getFetchJobDetails());
        query.setParameter("jobId", jobId);
        LOG.info("Query is:: .." + query);
        query.unwrap(SQLQuery.class).addScalar("job_status", StringType.INSTANCE)
                .addScalar("end_date", TimestampType.INSTANCE)
                .addScalar("error_message", StringType.INSTANCE);
        List<Object[]> resultList = query.getResultList();
        DateFormat dtFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        for (Object[] objects : resultList) {
            customBean = new CustomJobBean();
            customBean.setJob_status((String) objects[0]);
            Timestamp  st = (Timestamp ) objects[1];
            java.util.Date stDt = new java.util.Date(st.getTime());
            String strDate = dtFormatter.format(stDt);
            customBean.setEnd_date(strDate);
            customBean.setError_message((String) objects[2]);
        }
        return customBean;
}
    

    public List<AssetDetails> getInvalidAssets() {
        
        List<AssetDetails> invalidAssetsList = new ArrayList<AssetDetails>();
        Query query = em.createNativeQuery(util.getInvalidAssetDetails());
        LOG.info("After Query");
        query.unwrap(SQLQuery.class).addScalar("assetId", LongType.INSTANCE)
        .addScalar("assetName", StringType.INSTANCE)
        .addScalar("policyId", IntegerType.INSTANCE)
        .addScalar("custName", StringType.INSTANCE)
        .addScalar("fileSize", LongType.INSTANCE)
        .addScalar("slname", StringType.INSTANCE);
        
        List<Object[]> resultList = query.getResultList();
        LOG.info("After Resultset");
        Map<Long, AssetDetails> invalidAssets = new HashMap<Long, AssetDetails>();
        for (Object[] objects : resultList) {
              Long assetId = (Long) objects[0];
              AssetDetails assetDetails = invalidAssets.get(assetId);
              if(assetDetails == null) {
                     assetDetails = new AssetDetails();
                     List<String> slNameList = new ArrayList<String>();
                     assetDetails.setId(assetId);
                     assetDetails.setName((String) objects[1]);
                     assetDetails.setPolicyId((Integer) objects[2]);
                     assetDetails.setCustName((String) objects[3]);
                     assetDetails.setFilesize((Long) objects[4]);
                     slNameList.add((String) objects[5]);
                     assetDetails.setSlNameList(slNameList); 
                     invalidAssets.put(assetId, assetDetails);
              } else {
                     assetDetails.getSlNameList().add((String) objects[5]);
              }
              invalidAssetsList.add(assetDetails);
        }

        return invalidAssetsList;
    }

	public List getCustomerDetails(SearchCriteria criteria) {
		return null;
	}
	
	public Map<String,Integer> getLocationsIds(){
        List<Map<String,Integer>> storageLocationList = new ArrayList<Map<String,Integer>>();
        Query query = em.createNativeQuery(util.getLocationsList());
        query.unwrap(SQLQuery.class).addScalar("name", StringType.INSTANCE).addScalar("id", IntegerType.INSTANCE);
        Map<String,Integer> detailsMap=new HashMap<String,Integer>();
        AssetDetails details=null;
        List<Object[]> resList = query.getResultList();
        for (Object[] objects : resList) {
            details = new AssetDetails();
            details.setStorageLocationName((String) objects[0]);
            details.setStorageLocationId((Integer) objects[1]);
            detailsMap.put(details.getStorageLocationName(),details.getStorageLocationId());
            
        }
        return detailsMap;
    }
	
	/**
    * This method will fetch all the policies in the DB.
    * @retun list policies.
    */
    @SuppressWarnings("unchecked")
	public List<Policy> getPolicies() {
    	 Query query = em.createNamedQuery("allPolicies");
    	 List<Policy> policyList = (List<Policy>) query.getResultList();
    	 return policyList;
    }
    
    public List<CustomPolicySiteInfo> getSiteInfo(Long pid) {
    	Query query = em.createNativeQuery(util.getPolicySiteInfo());
    	query.setParameter("pid", pid);
    	query.unwrap(SQLQuery.class).addScalar("SITEID", LongType.INSTANCE)
    	.addScalar("NAME", StringType.INSTANCE)
    	.addScalar("TIER", StringType.INSTANCE)
    	.addScalar("ISPRIMARY", BooleanType.INSTANCE)
    	.addScalar("REQUIREDCOPIES", IntegerType.INSTANCE);
    	List<Object[]> resultList = query.getResultList();
    	
    	List<CustomPolicySiteInfo> customPolicySiteInfoList = new ArrayList<CustomPolicySiteInfo>();
    	for(Object[] record : resultList) {
    		CustomPolicySiteInfo policySiteInfo = new CustomPolicySiteInfo();
    		policySiteInfo.setSiteId((Long) record[0]);
    		policySiteInfo.setName((String) record[1]);
    		policySiteInfo.setTier((String) record[2]);
    		policySiteInfo.setIsPrimary((Boolean) record[3]);
    		policySiteInfo.setRequiredCopies((Integer) record[4]);
    		customPolicySiteInfoList.add(policySiteInfo);
    	}
    	return customPolicySiteInfoList;
    }
    
    public int updatePolicyForAsset(Long pid, Long assetId) {
    	Query query = em.createNativeQuery(util.getUpdatePolicyForAssetQuery());
    	query.setParameter("pid", pid);
    	query.setParameter("assetId", assetId);
    	
    	int updatedCount = query.executeUpdate();
    	return updatedCount;
    }
    
    public String addAssetForUploadedFile(Long pid, MultipartFile mpf) {
		try {
			Query absPathQuery = em.createNativeQuery(util.getAbsolutePathForPolicy());
			absPathQuery.unwrap(SQLQuery.class).addScalar("ABS_PATH", StringType.INSTANCE);
			absPathQuery.setParameter("pid", pid);
			absPathQuery.setParameter("landingZoneId", util.getLandingZoneId());
			String absPath = (String) absPathQuery.getSingleResult();
			
			FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(absPath + "/" + mpf.getOriginalFilename()));
//			FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("C:/Users/KCSunkara/Temp/" + mpf.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
		}

    	Query fsPathQuery = em.createNativeQuery(util.getFsPathForPolicy());
    	fsPathQuery.unwrap(SQLQuery.class).addScalar("FS_PATH", StringType.INSTANCE);
    	fsPathQuery.setParameter("pid", pid);
    	String fsPath = (String) fsPathQuery.getSingleResult();
    	
    	AssetDTO assetDTO = new AssetDTO();
    	assetDTO.setName(mpf.getOriginalFilename());
    	assetDTO.setFilesize(mpf.getSize());
    	assetDTO.setFs_path(fsPath.endsWith("/") ? 
    			fsPath + mpf.getOriginalFilename() : fsPath + "/" + mpf.getOriginalFilename());
    	
//    	"created_date": "08 Jun 2014 15:33:03.4432964160"
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    	assetDTO.setCreated_date(dateFormat.format(new Date()));
    	
    	List<AssetInstancesDTO> assetInstances = new ArrayList<AssetInstancesDTO>();
    	
    	AssetInstancesDTO instanceDTO = new AssetInstancesDTO();
    	instanceDTO.setFilename(mpf.getOriginalFilename());
    	instanceDTO.setStorageLocId(Long.parseLong(util.getLandingZoneId()));
    	
    	assetInstances.add(instanceDTO);
    	assetDTO.setAssetInstances(assetInstances);
    	
    	List<AssetDTO> assets = new ArrayList<AssetDTO>();
    	assets.add(assetDTO);
    	
    	AssetDTOList assetDTOList = new AssetDTOList();
    	assetDTOList.setAssets(assets);
    	
    	String inputJSON = null;
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.setSerializationInclusion(Inclusion.NON_NULL);
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
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity request= new HttpEntity( inputJSON, headers );

    	
    	RestTemplate restTemplate = new RestTemplate();
    	MessageResponse response = restTemplate.postForObject(util.getAddAssetServiceUrl(), request, MessageResponse.class);
    	
    	if( HttpStatus.CREATED.toString().equals(response.getCode()) ) {
    		return response.getMessage().equals("Asset already exists") ? 
    				"Asset ID: " + response.getAssetId() + " already exists." : "Asset ID: " + response.getAssetId() + " is created.";
    	}
    	return "Something went wrong in /fileasset WebService...";
    }

}
