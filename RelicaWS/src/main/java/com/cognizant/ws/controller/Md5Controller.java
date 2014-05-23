package com.cognizant.ws.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cognizant.ws.domain.MD5Params;
import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.exception.RequestMD5Exception;
import com.cognizant.ws.service.Md5Service;
import com.cognizant.ws.util.ReplicaWSConstants;

@Controller
public class Md5Controller {

	private static final Logger LOG = Logger.getLogger(Md5Controller.class);

	@Autowired
	private Md5Service md5service;

	/**
	 * This method is used to find the assets which are require for MD5
	 * and creates the batch job
	 * 
	 * @param md5params
	 * @return
	 * @throws RequestMD5Exception
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/requestmd5", method = RequestMethod.POST)
	public ResponseEntity<MessageResponse> requestmd5(
			@RequestBody MD5Params md5params) throws RequestMD5Exception,
			UnsupportedEncodingException {
		LOG.debug("Provider has received request from StorageId: "
				+ md5params.getStorageId());
		MessageResponse em = new MessageResponse();
		if (md5params.getStorageId() == null || md5params.getHostName() == null) {
			if (md5params.getStorageId() == null) {
				em.setMessage("StorageId should not be null");
				em.setDescription("StorageId should not be null");
			} else {
				em.setMessage("Host Name should not be null");
				em.setDescription("Host Name should not be null");
			}

		} else {
			Map<String, String> batchDetails = md5service.requestMD5(
					md5params.getStorageId(), md5params.getHostName());
			String jobId = batchDetails.get(ReplicaWSConstants.JOB_ID);
			LOG.info("JobId:..." + jobId);

			em.setCode(HttpStatus.OK + "");
			em.setJobId(jobId);
			em.setPath(batchDetails.get(ReplicaWSConstants.PATH));
			em.setError("");
			if (jobId != null && !jobId.equals("0")) {
				em.setMessage("batch id generated successfully");
				em.setDescription(" batch id generated successfully");
			} else {
				em.setMessage(batchDetails.get("description"));
				em.setDescription(batchDetails.get("description"));
			}
		}
		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);
	}

	/**
	 * This method is used to update the batch job based on the MD5 and
	 * error message.
	 * 
	 * @param md5params
	 * @return
	 * @throws RequestMD5Exception
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/responsemd5", method = RequestMethod.POST)
	public ResponseEntity<MessageResponse> responsemd5(
			@RequestBody MD5Params md5params) throws RequestMD5Exception,
			UnsupportedEncodingException {
		LOG.debug("Provider has received request to get storagename: "
				+ md5params.getBatchId());
		String responseStatus = "Successfully updated";
		String errorCode = md5params.getErrorCode();
		LOG.info("BatchId:..." + md5params.getBatchId());
		LOG.info("ParamMD5:..." + md5params.getParamMd5());
		LOG.info("ErrorCode:.." + errorCode);
		int returnVal = md5service.responseMd5(md5params.getBatchId(),
				md5params.getParamMd5(), errorCode);

		if (returnVal == 0) {
			responseStatus = "Failed to update";
		}
		if (errorCode != null && !errorCode.equals("")) {
			responseStatus = responseStatus + " errorCode : " + errorCode;
		} else {
			responseStatus = responseStatus + " MD5 : "
					+ md5params.getParamMd5();
		}
		MessageResponse em = new MessageResponse();
		em.setCode(String.valueOf(returnVal));
		em.setMessage(responseStatus);
		em.setDescription(responseStatus);
		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);
	}

}
