package com.cognizant.ws.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.service.CopyTierService;

/**
 * REST service provider
 * 
 * Only GET and POST will return values PUT and DELETE will not.
 */

@Controller
public class CopyTierController {

	private static final Logger LOG = Logger
			.getLogger(CopyTierController.class);

	@Autowired
	private CopyTierService copyTierService;

	/**
	 * This request method will fetch all the CopyTier1 eligible assets
	 * from database based on the policy sites
	 * 
	 * @param storageId
	 * @return
	 * @throws ReplicaWSException
	 */
	@RequestMapping(value = "/requestCopyTier/{hostName}/{storageId}", method = RequestMethod.GET)
	public ResponseEntity<List<MessageResponse>> requestCopyTier1(
			@PathVariable("hostName") String inputHostName,
			@PathVariable("storageId") Long storageId)
			throws ReplicaWSException {

		LOG.debug("COPY WS Provider has received request from : " + inputHostName +"with storageId: " + storageId);
		List<MessageResponse> mrList = copyTierService.requestCopyTier(storageId, inputHostName);
		
		LOG.debug("Finding alternate source node to route the ASCP request...");
		List<MessageResponse> outputMsgList = copyTierService.getAlernateHost(mrList, inputHostName);
		
		return new ResponseEntity<List<MessageResponse>>(outputMsgList, HttpStatus.OK);

	}

	/**
	 * This response method will update the copyTier1 status to database based on the policy sites
	 * 
	 * @param batchId
	 * @param status
	 * @return
	 * @throws ReplicaWSException
	 */
	@RequestMapping(value = "/responseCopyTier/{batchId}/{status}", method = RequestMethod.GET)
	public ResponseEntity<MessageResponse> responseCopyTier1(
			@PathVariable("batchId") Long batchId,
			@PathVariable("status") String status) throws ReplicaWSException {
		LOG.debug("Provider has received request to get storagename: " + batchId);

		MessageResponse em = copyTierService.responseCopyTier(batchId, status);

		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);

	}

}
