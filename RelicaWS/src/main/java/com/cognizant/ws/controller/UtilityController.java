package com.cognizant.ws.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognizant.ws.domain.Customer;
import com.cognizant.ws.domain.CustomerSummaryDTO;
import com.cognizant.ws.domain.CustomerUsageServiceParams;
import com.cognizant.ws.domain.Policy;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.pojo.Asset;
import com.cognizant.ws.service.UtilityService;

@Controller
public class UtilityController {

	@Autowired
	private UtilityService utilityService;
	
	private static final Logger LOG = Logger.getLogger(UtilityController.class);
	
	@RequestMapping(value = "/getPolicyDetails/{policyId}", method = RequestMethod.GET)
	public @ResponseBody Policy getAssetPolicyDetails(
			@PathVariable("policyId") Long policyId) {
		LOG.debug("getPolicyDetails controller invoked");
		Policy policy = utilityService.getPolicyDetails(policyId);
		return policy;
	}
	
	@RequestMapping(value = "/getCustomerDetails/{customerId}", method = RequestMethod.GET)
	public @ResponseBody Customer getCustomerDetails(
			@PathVariable("customerId") Long customerId) {
		LOG.debug("getCustomerDetails controller invoked");
		Customer customer = utilityService.getCustomerDetails(customerId);
		return customer;
	}
	
	@RequestMapping(value = "/getCustomerSummary/{customerId}", method = RequestMethod.GET)
	public @ResponseBody CustomerSummaryDTO getCustomerSummary(
			@PathVariable("customerId") Long customerId) {
		LOG.debug("getCustomerSummary controller invoked");
		CustomerSummaryDTO summaryDTO = utilityService.getCustomerSummary(customerId);
		return summaryDTO;
	}
	
	@RequestMapping(value = "/getAssetDetailsByJCID/{assetId}", method = RequestMethod.GET)
	public @ResponseBody Asset getAssetDetailsByJCID(
			@PathVariable("assetId") Long assetId) {
		LOG.debug("getCustomerSummary controller invoked");
		Asset asset = utilityService.getAssetDetailsByJCID(assetId);
		return asset;
	}
	
	@RequestMapping(value = "/getAssetDetailsByDAMID/{dam_internal_id}/{repository}", method = RequestMethod.GET)
	public @ResponseBody Asset getAssetDetailsByDAMID(
			@PathVariable("dam_internal_id") String dam_internal_id,
			@PathVariable("repository") String repository) {
		LOG.debug("getCustomerSummary controller invoked");
		Asset asset = utilityService.getAssetDetailsByDAMID(dam_internal_id, repository);
		return asset;
	}
	
	@RequestMapping(value = "/getCustomerUsage", method = RequestMethod.POST)
	public @ResponseBody Map<Date, Map<String, Long>> getCustomerUsage( 
			@RequestBody CustomerUsageServiceParams params) throws ReplicaWSException {
		LOG.info("CustomerUsage service START: "+Calendar.getInstance().getTime());
		LOG.info("CustomerUsage Service Request: startDate = " + params.getStartDate() + ", endDate = " + params.getEndDate() + 
				", customerId = " + params.getCustomerId());
		
		Map<Date, Map<String, Long>> customerUsageMap = utilityService.getCustomerUsage(params);
		
		LOG.info("CustomerUsage Service Response: "+ customerUsageMap);
		LOG.info("CustomerUsage service END: "+Calendar.getInstance().getTime());
		return customerUsageMap;
	}

}
