package com.cognizant.ui.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ui.beans.CustomPolicySiteInfo;
import com.cognizant.ui.beans.SearchCriteria;
import com.cognizant.ui.json.Customer;
import com.cognizant.ui.model.AssetDetails;
import com.cognizant.ui.model.CustomJobBean;
import com.cognizant.ui.model.Policy;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardDAO dashBoardDao;

	/**
	 * This method will fetch the asset details from database based on the
	 * search criteria.
	 * 
	 * @param criteria
	 * @return list of assets
	 */
	public List getAssets(SearchCriteria criteria) {
		List assets = dashBoardDao.getAssets(criteria);
		return assets;
	}

	/**
	 * This method will fetch the asset and asset instances details for the
	 * selected asset in asset search page.
	 * 
	 * @param criteria
	 * @retun list of asset details.
	 */
	public List getAssetDetails(SearchCriteria criteria) {
		// calling the Dao method to fetch the details from DB
		List assetDetails = dashBoardDao.getAssetDetails(criteria);
		return assetDetails;
	}

	/**
	 * This method will fetch the counts of the jobs from batchjobs table as per
	 * the status.
	 * 
	 * @param modelMap
	 * @param jobDate
	 * @return
	 */
	public CustomJobBean getJobCountForAllJobTypes(String jobDate) {
		CustomJobBean cb = dashBoardDao.getJobCountForAllJobTypes(jobDate);
		return cb;
	}

	/**
	 * This method will fetch the details of jobs based on the jobType selected.
	 * 
	 * @param jobType
	 * @param day
	 * @param jobStatus
	 * @return
	 */
	public List getJobsByType(String jobType, int day, String jobStatus) {
		List cb = dashBoardDao.getJobsByType(jobType, day, jobStatus);
		return cb;
	}

	/**
	 * This method will fetch the customer and customer instances details for
	 * the selected customer id.
	 * 
	 * @param criteria
	 * @retun list of asset details.
	 */
	public List<Customer> getCustomerNames() {
		List<Customer> customerDetails = dashBoardDao.getCustomerNames();
		return customerDetails;
	}

	public CustomJobBean getJobDetails(Long jobId) {
		CustomJobBean customerDetails = dashBoardDao.getJobDetails(jobId);
		return customerDetails;
	}

	public List<AssetDetails> getInvalidAssets() {
		List<AssetDetails> invaliedAssets = dashBoardDao.getInvalidAssets();
		return invaliedAssets;
	}

	public Map<String, Integer> getLocationsIds() {
		Map<String, Integer> locationIds = dashBoardDao.getLocationsIds();
		return locationIds;
	}

	/**
	 * This method will fetch all the policies in the DB.
	 * 
	 * @retun list policies.
	 */
	public List<Policy> getPolicies() {
		return dashBoardDao.getPolicies();
	}

	public List<CustomPolicySiteInfo> getSiteInfo(Long pid) {
		return dashBoardDao.getSiteInfo(pid);
	}

}
