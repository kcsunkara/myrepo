package com.cognizant.ws.service;

import java.util.List;

import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.exception.ReplicaWSException;

public interface CopyTierService {

	/**
	 * This request method will fetch all the CopyTier eligible assets
	 * from database based on the policy sites
	 * 
	 * @param storageId
	 * @return
	 * @throws ReplicaWSException
	 */
	public List<MessageResponse> requestCopyTier(Long storageId, String inputHostName)
			throws ReplicaWSException;

	/**
	 * This response method will update the copyTier1 status to 
	 * database based on the policy sites
	 * 
	 * @param batchId
	 * @param status
	 * @return
	 * @throws ReplicaWSException
	 */
	public MessageResponse responseCopyTier(Long batchId, String status)
			throws ReplicaWSException;
	
	/**
	 * This method with find and replace the actual source hostname with the alternate hostname 
	 * to route copy request. 
	 * @param messageResponseList
	 * @param inputHostName Host name that invoked copy service
	 * @return messageResponseList MessageResopnse list with hostname replaced with alternate hostname 
	 * @throws ReplicaWSException
	 */
	public List<MessageResponse> getAlernateHost(List<MessageResponse> messageResponseList, String inputHostName) throws ReplicaWSException;
	
}
