package com.cognizant.ws.service;

import java.util.Map;

import com.cognizant.ws.exception.RequestMD5Exception;

public interface Md5Service {

	/**
	 * This method will get the request Md5 based on the storage id.
	 * @param storageId
	 * @param hostName
	 * @return
	 * @throws RequestMD5Exception
	 */
	public Map<String, String> requestMD5(Long storageId, String hostName)
			throws RequestMD5Exception;

	/**
	 * This method will get the request Md5 based on the storage name.
	 * 
	 * @param batch_id
	 * @param asset_instance_id
	 * @param error_message
	 * @return success or error message
	 */
	public int responseMd5(Long batchId, String paramMd5, String errorCode)
			throws RequestMD5Exception;

}
