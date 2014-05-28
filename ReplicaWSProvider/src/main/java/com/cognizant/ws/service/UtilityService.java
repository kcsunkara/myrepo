package com.cognizant.ws.service;

import java.util.Date;
import java.util.Map;

import com.cognizant.ws.domain.Customer;
import com.cognizant.ws.domain.CustomerSummaryDTO;
import com.cognizant.ws.domain.CustomerUsageServiceParams;
import com.cognizant.ws.domain.Policy;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.pojo.Asset;

public interface UtilityService {
	
	public Policy getPolicyDetails(Long policyId);
	public Customer getCustomerDetails(Long customerId);
	public CustomerSummaryDTO getCustomerSummary(Long customerId);
	public Asset getAssetDetailsByAssetID(Long assetId);
	public Map<Date, Map<String, Long>> getCustomerUsage(CustomerUsageServiceParams params) throws ReplicaWSException;

}
