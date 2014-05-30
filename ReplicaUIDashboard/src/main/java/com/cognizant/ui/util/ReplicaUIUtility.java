package com.cognizant.ui.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ReplicaUIUtility {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReplicaUIUtility.class);
	
		
		@Value("${getJobCountForAllJobTypes.sql}")
		private String jobCountForAllJobTypes;
		
		@Value("${getAllJobsByType.sql}")
		private String allJobsByType;
		
		@Value("${getAllNewJobsByType.sql}")
		private String allNewJobsByType;

		@Value("${getInvalidAssetCount.sql}")
		private String invalidAssetCount;
		
	    @Value("${getInvalidAssetDetails.sql}")
	    private String invalidAssetDetails;
	    
	    @Value("${getCustomerNames.sql}")
	    private String customeNames;
	    
        @Value("${getJobDetails.sql}")
        private String fetchJobDetails;	 
        
        @Value("${ws.constomer.details.url}")
        private String customerDetailsServiceUrl;
        
        @Value("${ws.constomer.usage.url}")
        private String customerUsageServiceUrl;
        
        @Value("${getLocations.sql}")
        private String locationsList;
        
	    

		public String getAllNewJobsByType() {
			return allNewJobsByType;
		}


		public void setAllNewJobsByType(String allNewJobsByType) {
			this.allNewJobsByType = allNewJobsByType;
		}


		public String getAllJobsByType() {
			return allJobsByType;
		}


		public void setAllJobsByType(String allJobsByType) {
			this.allJobsByType = allJobsByType;
		}


		public String getJobCountForAllJobTypes() {
			return jobCountForAllJobTypes;
		}


		public void setGetJobCountForAllJobTypes(String jobCountForAllJobTypes) {
			this.jobCountForAllJobTypes = jobCountForAllJobTypes;
		}


        
        
        public String getInvalidAssetCount() {
        
            return invalidAssetCount;
        }


        
        
        public void setInvalidAssetCount(String invalidAssetCount) {
        
            this.invalidAssetCount = invalidAssetCount;
        }


        
        
        public String getInvalidAssetDetails() {
        
            return invalidAssetDetails;
        }


        
        
        public void setInvalidAssetDetails(String invalidAssetDetails) {
        
            this.invalidAssetDetails = invalidAssetDetails;
        }


        
        
        public String getCustomeNames() {
        
            return customeNames;
        }


        
        
        public void setCustomeNames(String customeNames) {
        
            this.customeNames = customeNames;
        }


        
        
        public String getFetchJobDetails() {
        
            return fetchJobDetails;
        }


        
        
        public void setFetchJobDetails(String fetchJobDetails) {
        
            this.fetchJobDetails = fetchJobDetails;
        }

		public String getCustomerDetailsServiceUrl() {
			return customerDetailsServiceUrl;
		}


		public void setCustomerDetailsServiceUrl(String customerDetailsServiceUrl) {
			this.customerDetailsServiceUrl = customerDetailsServiceUrl;
		}


		public String getCustomerUsageServiceUrl() {
			return customerUsageServiceUrl;
		}


		public void setCustomerUsageServiceUrl(String customerUsageServiceUrl) {
			this.customerUsageServiceUrl = customerUsageServiceUrl;
		}


		public String getLocationsList() {
			return locationsList;
		}


		public void setLocationsList(String locationsList) {
			this.locationsList = locationsList;
		}

}