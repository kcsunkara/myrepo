package com.hbo.itdmg.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hbo.itdmg.webservice.UUIDClient;
import com.hbo.itdmg.webservice.UUIDService;


@Controller
@RequestMapping("/")
public class UUIDClientController {

	private static Logger LOG = Logger.getLogger(UUIDClientController.class.getName());
	
	private String applicationURI;

	private UUIDClient uuidClientSOAP;
	private UUIDClient uuidClientREST;
	
	@Autowired
	private UUIDService uuidService;
	private UUIDClient getUUIDClient(HttpServletRequest request, UUIDClient.CLIENT_TYPE clientType) {
		
		if (clientType.equals(UUIDClient.CLIENT_TYPE.SOAP)) {
			if (uuidClientSOAP == null) {
				uuidClientSOAP = new UUIDClient(getApplicationURI(request), UUIDClient.CLIENT_TYPE.SOAP);
			}
			
			return uuidClientSOAP;
		}
		
		if (clientType.equals(UUIDClient.CLIENT_TYPE.REST)) {
			if (uuidClientREST == null) {
				uuidClientREST = new UUIDClient(getApplicationURI(request), UUIDClient.CLIENT_TYPE.REST);
			}
			
			return uuidClientREST;
		}
		
		
		return null;
	}
	
	/**
	 * returns the URI of the application 
	 * @param req
	 * @return
	 */
	private String getApplicationURI(HttpServletRequest req) {
		if (applicationURI == null) {
			String port;
			
		    if ("http".equalsIgnoreCase(req.getScheme()) && req.getServerPort() != 80 ||
		            "https".equalsIgnoreCase(req.getScheme()) && req.getServerPort() != 443 ) {
		        port = ":" + req.getServerPort();
		    } else {
		        port = "";
		    }
		    
	        applicationURI = req.getScheme() + "://" + req.getServerName() +
	                port + req.getContextPath();
	        
	        LOG.debug("Application URL was set to: " + applicationURI );
		}
		
		return applicationURI;
	}
	
	@RequestMapping(value="/createUUID", method = RequestMethod.GET)
	public @ResponseBody String createUUID(HttpServletRequest request) {
		LOG.debug("Creating UUID...");
		return getUUIDClient(request, UUIDClient.CLIENT_TYPE.fromString("REST")).createUUID();
		//return uuidService.createUUID();
	}
	
	@RequestMapping(value="/createUUID/{uuidCount}", method = RequestMethod.GET)
	public  @ResponseBody  Collection<String> createUUID(@PathVariable Integer uuidCount, HttpServletRequest request) {
		LOG.debug("Creating "+uuidCount.intValue()+" UUIDs...");
		return getUUIDClient(request, UUIDClient.CLIENT_TYPE.fromString("REST")).createUUIDs(uuidCount);
		//return uuidService.createUUIDs(uuidCount);
	}
	
}
