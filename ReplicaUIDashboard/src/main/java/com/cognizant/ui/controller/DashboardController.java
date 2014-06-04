package com.cognizant.ui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.cognizant.ui.beans.CustomPolicySiteInfo;
import com.cognizant.ui.beans.SearchCriteria;
import com.cognizant.ui.dao.DashboardService;
import com.cognizant.ui.json.Customer;
import com.cognizant.ui.json.CustomerDTO;
import com.cognizant.ui.json.CustomerUsageDTO;
import com.cognizant.ui.model.AssetDetails;
import com.cognizant.ui.model.CustomJobBean;
import com.cognizant.ui.model.Policy;
import com.cognizant.ui.util.ReplicaUIUtility;
import com.google.gson.Gson;


/**
 * This is the Replica UI Dashboard controller 
 * @author nakkc001
 *
 */
@Controller
//@SessionAttributes("customerDetails")
@SessionAttributes("csvData")

public class DashboardController {

	private static final Logger LOG = Logger.getLogger(DashboardController.class);

	@Autowired
	private DashboardService dashboardService;
    
	@Autowired
    ReplicaUIUtility util;

	/**
	 * This method will redirect the user to Search Screen.
	 * @return
	 */
	@RequestMapping(value = "/searchAsset.html", method = RequestMethod.GET)
	public String displaySearchAssetHomePage() {
		return "assetSearch";
	}

	/**
	 * This method will fetch the assets from database based on the Search
	 * Criteria.
	 * 
	 * @param assetID
	 * @param name
	 * @param path
	 * @param modelMap
	 * @return
	 */

	@RequestMapping(value = "/searchAsset.html", method = RequestMethod.POST)
	public String searchAsset(
			@RequestParam(value = "assetId", required = false) Long assetID,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "path", required = false) String path,
			ModelMap modelMap) {

		LOG.debug("searchAsset.." + modelMap);
		SearchCriteria criteria = new SearchCriteria();
		criteria.setAssetId(assetID);
		criteria.setName(name);
		criteria.setPath(path);
		List assets = dashboardService.getAssets(criteria);
		modelMap.put("assets", assets);
		return "assetSearch";
	} 

	/**
	 * This method will fetch the asset and assetInstances details from database based on the assetId
	 * 
	 * @param assetID
	 * @param policyId
	 * @param customerName
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/assetDetails.html", method = RequestMethod.GET)
	public String displayAssetDetails(
			@RequestParam(value = "assetId", required = false) Long assetID,
			@RequestParam(value = "policyId", required = false) Long policyId,
			@RequestParam(value = "customerName", required = false) String customerName,
			ModelMap modelMap) {

		LOG.debug("displayAssetDetails.." + modelMap);

		SearchCriteria criteria = new SearchCriteria();

		criteria.setAssetId(assetID);
		criteria.setPolicyId(policyId);
		criteria.setCustomerName(customerName);
		// calling the Dao method to fetch the details from DB
		List assetDetails = dashboardService.getAssetDetails(criteria);

		LOG.debug("assetDetails.." + assetDetails);

		modelMap.put("assetDetails", assetDetails);

		return "assetDetails";
	}
	
	/**
	 * This method will fetch the counts of the jobs from batchjobs table as per the status.
	 * @param modelMap
	 * @param jobDate
	 * @return
	 */
    @RequestMapping(value = "/queueManager.html", method = RequestMethod.GET)
    public String  queueManager(ModelMap modelMap,@RequestParam(value = "jobDate", required = false) String jobDate) {
    	
    	LOG.info("Job period selected for search"+jobDate);
    	
    	CustomJobBean jobDetails = dashboardService.getJobCountForAllJobTypes(jobDate);
    	
    	
    	modelMap.put("CopyTier1RunningCount", jobDetails.getCopyTier1RunningCount());
    	modelMap.put("CopyTier1CompletedCount", jobDetails.getCopyTier1CompletedCount());
    	modelMap.put("CopyTier1ErrorCount", jobDetails.getCopyTier1ErrorCount());
    	
    	modelMap.put("md5Total", jobDetails.getMd5Total());
    	modelMap.put("copytier1Total", jobDetails.getCopytier1Total());
    	modelMap.put("copytier2Total", jobDetails.getCopytier2Total());
    	modelMap.put("restoreTotal", jobDetails.getRestoreTotal());
    	
    	modelMap.put("totalRunning", jobDetails.getTotalRunning());
    	modelMap.put("totalFailed", jobDetails.getTotalFailed());
    	modelMap.put("totalCompleted", jobDetails.getTotalCompleted());
    	
    	modelMap.put("finalTotal", jobDetails.getFinalTotal());
    	
    	modelMap.put("jobDetails", jobDetails);
    	modelMap.put("invalidAssetCount", jobDetails.getInvalidAssetCount());

		return "queueManager";
    	
    }
    
    /**
     * This method fetches the jobtypes and values based on the number days selected by user.
     * @param modelMap
     * @param jobDate
     * @return
     */
    @RequestMapping(value = "/jobsCountByDay.html", method = RequestMethod.POST)
    public @ResponseBody CustomJobBean  jobsCountByDay(ModelMap modelMap,@RequestParam(value = "jobDate", required = false) String jobDate) {
    	
    	LOG.info("Job period selected for search"+jobDate);
    	
    	CustomJobBean jobDetails = dashboardService.getJobCountForAllJobTypes(jobDate);
    	
    	
    	modelMap.put("CopyTier1RunningCount", jobDetails.getCopyTier1RunningCount());
    	modelMap.put("CopyTier1CompletedCount", jobDetails.getCopyTier1CompletedCount());
    	modelMap.put("CopyTier1ErrorCount", jobDetails.getCopyTier1ErrorCount());
    	
    	modelMap.put("md5Total", jobDetails.getMd5Total());
    	modelMap.put("copytier1Total", jobDetails.getCopytier1Total());
    	modelMap.put("copytier2Total", jobDetails.getCopytier2Total());
    	modelMap.put("restoreTotal", jobDetails.getRestoreTotal());
    	
    	modelMap.put("totalRunning", jobDetails.getTotalRunning());
    	modelMap.put("totalFailed", jobDetails.getTotalFailed());
    	modelMap.put("totalCompleted", jobDetails.getTotalCompleted());
    	
    	modelMap.put("finalTotal", jobDetails.getFinalTotal());
    	
    	modelMap.put("jobDetails", jobDetails);

		
    	return jobDetails;
    }
    
    /**
     * This method will fetch the details of jobs based on the jobType selected.
     * @param jobType
     * @param day
     * @param jobStatus
     * @return
     */
    @RequestMapping(value = "/getJobsByType.html")
	public     @ResponseBody List<CustomJobBean> getJobsByType(
			@RequestParam(value = "jobType", required = false) String jobType,
			@RequestParam(value = "day", required = false) int day,
			@RequestParam(value = "jobStatus", required = false) String jobStatus)
    { 	
    	LOG.info("Fetching the results for jobType::"+jobType+" With Status: "+jobStatus);
    	List<CustomJobBean> jobDetails = dashboardService.getJobsByType(jobType,day,jobStatus);
    	return jobDetails;
  
    }
    
    /**
     * This method will fetch the customer and assetInstances details from database based on the customerId
     * @param customerId
     * @return
     */
    @RequestMapping(value="/customerDetails1.html",method=RequestMethod.GET)
    public String getCustomerNames(ModelMap modelMap){
         
        LOG.debug("Retrieve customer details.." + modelMap);

        List<Customer> customerDetails = dashboardService.getCustomerNames();

        LOG.debug("customerDetails.." + customerDetails);

        modelMap.put("customerDetails", customerDetails);

        return "customerDetails1";
        
    }
    
    
    /**
     * This method will fetch the customer and assetInstances details from database based on the customerId
     * @param customerId
     * @return
     * @throws FileNotFoundException 
     */
    
    @RequestMapping(value = "/displayCustomerDetails.html", method = RequestMethod.POST)
    public @ResponseBody CustomerDTO displayCustomerDetails(ModelMap modelMap,
            @RequestParam(value = "custName", required = false) Long customerId) throws FileNotFoundException {
    
        LOG.debug("displayAssetDetails.. Customer Nameis:: " + customerId);
        Gson gson = new Gson();
       
       RestTemplate restTemplate = new RestTemplate();
       
      CustomerDTO jsonDto =
                restTemplate.getForObject(util.getCustomerDetailsServiceUrl()+"/"+customerId
                        , CustomerDTO.class);
      
        LOG.info("Customer Object is:::: "+ jsonDto);
        return jsonDto;
        
    }
    
    
    /**
     * This method will fetch the details of jobs based on the jobType selected.
     * @param jobType
     * @param day
     * @param jobStatus
     * @return
     */
    @RequestMapping(value = "/getJobDetails.html", method = RequestMethod.POST )
    public     @ResponseBody CustomJobBean getJobDetails(
            @RequestParam(value = "jobId", required = false) Long jobId)
    {   
        LOG.info("Fetching the results for JobId::"+jobId);
        CustomJobBean jobDetails = dashboardService.getJobDetails(jobId);
        LOG.info("Job Status is::: "+jobDetails.getError_message());
        return jobDetails;
  
    }
     @RequestMapping(value = "/invalidAssets.html", method = RequestMethod.POST)
     public @ResponseBody List<AssetDetails> getInvalidAssets() {
         LOG.info("In controller getinvalidAssets");
         List<AssetDetails> invalidAssetsList = dashboardService
                 .getInvalidAssets();
             return invalidAssetsList;
     }
    
     @RequestMapping(value="/detailedUsageReport.html",method=RequestMethod.GET)
     public String detailedUsageReport(ModelMap modelMap){
          
         LOG.debug("Retrieve customer details.." + modelMap);

         List<Customer> customerDetails = dashboardService.getCustomerNames();

         LOG.debug("customerDetails.." + customerDetails);

         modelMap.put("customerDetails", customerDetails);

         return "detailedUsageReport";
         
     }
     
     @RequestMapping(value = "/getDetailedUsageReport.html", method = RequestMethod.POST)
     public @ResponseBody
     List getDetailedUsageRepost(ModelMap modelMap,
             @RequestParam(value = "custId", required = false) String customerId,
             @RequestParam(value = "startDate", required = false) Date sDate,
             @RequestParam(value = "endDate", required = false) Date eDate,HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException,
             ParseException {
         LOG.info("Start Date:"+sDate+" End date:  "+eDate);
         
         DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         String startDate= formatter.format(sDate);
         String endDate= formatter.format(eDate);
         RestTemplate restTemplate = new RestTemplate();
         CustomerUsageDTO custUsageDTO = new CustomerUsageDTO();
         custUsageDTO.setCustomerId(customerId);
         custUsageDTO.setStartDate(startDate);
         custUsageDTO.setEndDate(endDate);
         List usageList= new ArrayList();
         
         Map<Long,Map<String,Number>> jsonDto = restTemplate.postForObject(util.getCustomerUsageServiceUrl(),custUsageDTO,Map.class);
                        
         Map<String,Integer> storagelocationId=  dashboardService.getLocationsIds();
         SortedMap<String,String> storageLocationMap = new TreeMap<String, String>(); 
         Set<Object> keys=null;
         Properties props = new Properties();
         try {
         File sharedDir = new File(System.getProperty("catalina.base"), "shared");
         File sharedFile = new File(sharedDir, "replica_ui_db.properties");
         InputStream stream = new FileInputStream(sharedFile);
        
         props.load(stream);
             
         } catch (IOException e) {
             
             e.printStackTrace();
         }
         
                          
             DecimalFormat df = new DecimalFormat("###.###");
             Map<String, Number> innerMap = new HashMap<String, Number>();
             SortedMap <String,Double> nodeCostMap=  new TreeMap<String, Double>();
             for(Entry<String, Integer> entry : storagelocationId.entrySet()) {
                 nodeCostMap.put(entry.getKey(), 0.0);
             }
             
             
             TreeMap<String, String>  totalNodeValue= null;
             Map<Long, TreeMap<String, String>> finalTableMap = null;
             if(jsonDto.size()!=0)
             {
            	 finalTableMap = new TreeMap<Long, TreeMap<String,String>>();
             }
			for (Entry<Long, Map<String, Number>> datewiseMap : jsonDto.entrySet()) {
				totalNodeValue= new TreeMap<String, String>();
					 
				LOG.debug("Key : " + datewiseMap.getKey() + " Value : " + datewiseMap.getValue());
				
				innerMap = datewiseMap.getValue();
				for(Entry<String, Number> jsonMapEntry : innerMap.entrySet()) {
					Number lookupValue= jsonMapEntry.getValue();
					double size = lookupValue.longValue();
					 
					int id= storagelocationId.get(jsonMapEntry.getKey());
					double currentNodeCost = Double.valueOf(props.getProperty(id+""));
					
					double pSize= size/(1024*1024*1024) ;
					String perNodeCost=df.format((pSize*currentNodeCost));
					String perNodeSize=df.format(pSize);
					double nodeCost=Double.valueOf(perNodeCost);
					
					totalNodeValue.put(jsonMapEntry.getKey(), perNodeSize+" GB / $ "+perNodeCost);
					double totalCost=nodeCostMap.get(jsonMapEntry.getKey());
					nodeCostMap.put(jsonMapEntry.getKey(), Double.valueOf(df.format(totalCost+nodeCost)));
				}
				finalTableMap.put(datewiseMap.getKey(), totalNodeValue);
				
			}
			
                                     
             usageList.add(nodeCostMap.keySet());
             usageList.add(finalTableMap);
             usageList.add(nodeCostMap);
             
             
             LOG.debug("Customer Object is:::: " + jsonDto);
         return usageList;
         
     }
	 
	 @RequestMapping(value = "/downloadCSV.html", method = RequestMethod.GET)
     public void downloadCSV(ModelMap modelMap,
             @RequestParam(value = "custId", required = false) String customerId,
             @RequestParam(value = "startDate", required = false) Date sDate,
             @RequestParam(value = "endDate", required = false) Date eDate,HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException,
             ParseException, IOException {
        LOG.info("Start Date:"+sDate+" End date:  "+eDate);
		 
		DateFormat csvDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal= Calendar.getInstance();
        String csvFileName=csvDateFormat.format(cal.getTime())+".csv";
        
		LOG.debug("csv name is: " + csvFileName);
		
		resp.setContentType("text/csv");

        // creates mock data
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        resp.setHeader(headerKey, headerValue);
        PrintWriter pWriter = resp.getWriter();
        
         
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String startDate= formatter.format(sDate);
        String endDate= formatter.format(eDate);
        RestTemplate restTemplate = new RestTemplate();
        CustomerUsageDTO custUsageDTO = new CustomerUsageDTO();
        custUsageDTO.setCustomerId(customerId);
        custUsageDTO.setStartDate(startDate);
        custUsageDTO.setEndDate(endDate);
        List usageList= new ArrayList();
         
        Map<Long,Map<String,Number>> jsonDto = restTemplate.postForObject(util.getCustomerUsageServiceUrl(),custUsageDTO,Map.class);
                        
        Map<String,Integer> storagelocationId=  dashboardService.getLocationsIds();
        SortedMap<String,String> storageLocationMap = new TreeMap<String, String>(); 
        Set<Object> keys=null;
        Properties props = new Properties();
        try 
		{
			File sharedDir = new File(System.getProperty("catalina.base"), "shared");
			File sharedFile = new File(sharedDir, "replica_ui_db.properties");
			InputStream stream = new FileInputStream(sharedFile);
			
			props.load(stream);
				 
		} catch (IOException e) {
            e.printStackTrace();
         }
         
        StringBuffer buffer = new StringBuffer();
        pWriter.write("Date");
        pWriter.write(",");
             
        DecimalFormat df = new DecimalFormat("###.###");
        Map<String, Number> innerMap = new HashMap<String, Number>();
        SortedMap <String,Double> nodeCostMap=  new TreeMap<String, Double>();
        for(Entry<String, Integer> entry : storagelocationId.entrySet()) 
		{
          nodeCostMap.put(entry.getKey(), 0.0);
        }
             
        for(String name:nodeCostMap.keySet())
        {
            pWriter.write(name); 
            pWriter.write(",");
        }
        pWriter.write("\n");
                          
        TreeMap<String, String>  totalNodeValue= null;
        Map<Long, TreeMap<String, String>> finalTableMap = null;
        if(jsonDto.size()!=0)
        {
			finalTableMap = new TreeMap<Long, TreeMap<String,String>>();
        }
		for (Entry<Long, Map<String, Number>> datewiseMap : jsonDto.entrySet()) 
		{
			totalNodeValue= new TreeMap<String, String>();
					 
			LOG.debug("Key : " + datewiseMap.getKey() + " Value : " + datewiseMap.getValue());
			pWriter.write(datewiseMap.getKey()+",");
			innerMap = datewiseMap.getValue();
			for(Entry<String, Number> jsonMapEntry : innerMap.entrySet()) 
			{
				Number lookupValue= jsonMapEntry.getValue();
				double size = lookupValue.longValue();
						 
				int id= storagelocationId.get(jsonMapEntry.getKey());
				double currentNodeCost = Double.valueOf(props.getProperty(id+""));
						
				double pSize= size/(1024*1024*1024) ;
				String perNodeCost=df.format((pSize*currentNodeCost));
				String perNodeSize=df.format(pSize);
				double nodeCost=Double.valueOf(perNodeCost);
				pWriter.write(perNodeSize+" GB / $ "+perNodeCost+",");
				totalNodeValue.put(jsonMapEntry.getKey(), perNodeSize+" GB / $ "+perNodeCost);
				double totalCost=nodeCostMap.get(jsonMapEntry.getKey());
				nodeCostMap.put(jsonMapEntry.getKey(), Double.valueOf(df.format(totalCost+nodeCost)));
			}
			finalTableMap.put(datewiseMap.getKey(), totalNodeValue);
				
				pWriter.write("\n");
		}
			
        pWriter.write("Total");
        pWriter.write(",");
        for(Entry<String,Double> jsonMapEntry : nodeCostMap.entrySet()) 
		{
			pWriter.write("$ "+jsonMapEntry.getValue());
            pWriter.write(",");
        }
             
         
     }
	 
	 /**
	 * This method will redirect the user to Search Screen.
	 * @return
	 */
	@RequestMapping(value = "/policyInfo.html", method = RequestMethod.GET)
	public String policyInfo(
			@RequestParam(value = "pid", required = false) Long pid, 
			@RequestParam(value = "assetId", required = false) Long assetId, 
			ModelMap modelMap) {
		modelMap = getPolicySiteInfo(modelMap, pid, assetId);
		return "policyInformation";
	}
	
	private ModelMap getPolicySiteInfo(ModelMap modelMap, Long pid, Long assetId) {
		List<Policy> policyList = dashboardService.getPolicies();
		List<CustomPolicySiteInfo> policySiteInfoList = null;
		if(pid != null) {
			policySiteInfoList = dashboardService.getSiteInfo(pid);
		} else {
			policySiteInfoList = dashboardService.getSiteInfo(policyList.get(0).getId());
		}
		modelMap.put("policyId", pid);
		modelMap.put("assetId", assetId);
		modelMap.put("policyList", policyList);
		modelMap.put("policySiteInfo", policySiteInfoList);
		
		return modelMap;
	}
	
	@RequestMapping(value = "/siteInfoByPID.html")
	public @ResponseBody List<CustomPolicySiteInfo> getSiteInfoByPID(
			@RequestParam(value = "pid", required = false) Long pid) { 	
    	List<CustomPolicySiteInfo> policySiteInfoList = dashboardService.getSiteInfo(pid);
    	return policySiteInfoList;
    }
  
}
