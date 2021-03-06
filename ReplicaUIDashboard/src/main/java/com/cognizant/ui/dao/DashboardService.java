package com.cognizant.ui.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cognizant.ui.beans.CustomPolicySiteInfo;
import com.cognizant.ui.beans.SearchCriteria;
import com.cognizant.ui.json.Customer;
import com.cognizant.ui.model.AssetDetails;
import com.cognizant.ui.model.CustomJobBean;
import com.cognizant.ui.model.Policy;

public interface DashboardService {
	
	/**
	 * This method will fetch the asset details from database based on the
	 * search criteria.
	 * 
	 * @param criteria
	 * @return list of assets
	 */
	public List getAssets(SearchCriteria criteria);

	/**
	 * This method will fetch the asset and asset instances details for the
	 * selected asset in asset search page.
	 * 
	 * @param criteria
	 * @retun list of asset details.
	 */
	public List getAssetDetails(SearchCriteria criteria);
	
	/**
	 * This method will fetch the counts of the jobs from batchjobs table as per the status.
	 * @param modelMap
	 * @param jobDate
	 * @return
	 */
	public CustomJobBean getJobCountForAllJobTypes(String jobDate);
	
	 /**
     * This method will fetch the details of jobs based on the jobType selected.
     * @param jobType
     * @param day
     * @param jobStatus
     * @return
     */
	public List<CustomJobBean> getJobsByType(String jobType,int day,String jobStatus);
	
	/**
     * This method will fetch the customer and customer instances details for the
     * selected customer id.
     * 
     * @param criteria
     * @retun list of asset details.
     */
    public List<Customer> getCustomerNames();
    
    
    public CustomJobBean getJobDetails(Long jobId);
    
    public List<AssetDetails> getInvalidAssets();
    public Map<String,Integer> getLocationsIds();
    
    /**
     * This method will fetch all the policies in the DB.
     * 
     * @retun list policies.
     */
    public List<Policy> getPolicies();
    
    public List<CustomPolicySiteInfo> getSiteInfo(Long pid);
    
    public int updatePolicyForAsset(Long pid, Long assetId);
    
    public String addAssetForUploadedFile(Long pid, MultipartFile mpf);
}
