package com.cognizant.ws.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognizant.ws.domain.Asset;
import com.cognizant.ws.domain.AssetDTO;
import com.cognizant.ws.domain.AssetDTOList;
import com.cognizant.ws.domain.AssetList;
import com.cognizant.ws.domain.Policy;
import com.cognizant.ws.exception.AssetNotFoundException;
import com.cognizant.ws.exception.ErrorType;
import com.cognizant.ws.exception.MessageResponse;
import com.cognizant.ws.exception.PolicyNotFoundException;
import com.cognizant.ws.exception.ReplicaWSException;
import com.cognizant.ws.service.AssetService;

/**
 * REST service provider
 * Only GET and POST will return values PUT and DELETE will not.
 */
@Controller
public class AssetController {

	private static final Logger LOG = Logger.getLogger(AssetController.class);

	@Autowired
	private AssetService assetService;

	/**
	 * This method will fetch all the assets from JC database.
	 * @return
	 */
	@RequestMapping(value = "/assets", method = RequestMethod.GET)
	public @ResponseBody
	AssetList getAssets() {
		LOG.debug("Provider has received request to get all persons");

		AssetList result = new AssetList();
		result.setAsset(assetService.getAll());

		return result;
	}
	
	/**
	 * This method will fetch the assets based on the assetId
	 * @param id
	 * @return
	 * @throws AssetNotFoundException
	 */
	@RequestMapping(value = "/asset/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Asset getAsset(@PathVariable("id") Long id) throws AssetNotFoundException {
		LOG.debug("Provider has received request to get person with id: " + id);

		Asset asset = assetService.findByAssetId(id);

		if (asset == null) {
			throw new AssetNotFoundException("asset not found " + id);
		}

		return asset;
	}
	
	/**
	 * This method will ingest the File Assets to JC database.
	 * @param assetDTOList
	 * @return
	 * @throws ReplicaWSException
	 */
	@RequestMapping(value = "/fileasset", method = RequestMethod.POST)
	public ResponseEntity<List<MessageResponse>> addFileAsset(
			@Valid @RequestBody AssetDTOList assetDTOList)
			throws ReplicaWSException {

		AssetDTOList assetDTOLists = assetService.saveFileAssets(assetDTOList);

		DateFormat dm = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");

		MessageResponse em = null;
		List<MessageResponse> listMessageResponse = new ArrayList<MessageResponse>();
		for (AssetDTO assetDTO : assetDTOLists.getAssets()) {
			em = new MessageResponse();
			em.setCode(HttpStatus.CREATED + "");

			if (assetDTO.isAssetExists()) {
				em.setMessage("Asset already exists");
				em.setDescription("Asset already exists for this combination name:"
						+ assetDTO.getName()
						+ " filesize:"
						+ assetDTO.getFilesize()
						+ " fs_path:"
						+ assetDTO.getFs_path());
			} else {
				em.setMessage("Asset and AssetInstances are added successfully");
				em.setDescription("Asset and AssetInstances are added successfully");
			}

			em.setAssetId(assetDTO.getId());

			listMessageResponse.add(em);
		}

		return new ResponseEntity<List<MessageResponse>>(listMessageResponse,
				HttpStatus.CREATED);
	}

	/**
	 * This method will find a policy for the requested Asset. if the policy id
	 * is not exists in Asset table,It will fetch from Folders table based on
	 * the the asset's L-Path.
	 * 
	 * @param assetId
	 * @return policyId
	 * @throws AssetNotFoundException
	 */
	@RequestMapping(value = "/findAssetPolicy/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> findAssetPolicy(@PathVariable("id") Long assetId)
			throws AssetNotFoundException, PolicyNotFoundException {
		LOG.debug("Provider has received request to get a policy for an asset with id: "
				+ assetId);
		Map<String, String> policyMap = new HashMap<String, String>();
		policyMap.put("code", HttpStatus.OK.toString());
		policyMap.put("message", "SUCCESS");
		Asset asset = assetService.findByAssetId(assetId);
		if (asset == null) {
			throw new AssetNotFoundException("Asset not found");
		}
		
		Long policyId = asset.getPolicy_id();
		if (policyId != null && policyId != 0) {
			policyMap.put("policy_id", policyId.toString());
		} else {
			// get the policy id from the Folders table based on Asset's L-Path.
			Long assetPolicyId = assetService.findAssetPolicy(asset.getFs_path(), new ArrayList<Policy>());
			if (assetPolicyId.longValue() == 0) {
				throw new PolicyNotFoundException(
						"No Policy match found for asset: " + assetId);
			} else {
				policyMap.put("policy_id", assetPolicyId.toString());
			}
		}
		return policyMap;
	}

	/**
	 * This method will assign a Policy to an asset in JC-Database.
	 * 
	 * @param assetId
	 * @param asset
	 * @return
	 * @throws AssetNotFoundException
	 */
	@RequestMapping(value = "/assignPolicy/{id}/{policyId}", method = RequestMethod.PUT)
	public ResponseEntity<MessageResponse> updatePolicy(
			@PathVariable("id") Long assetId,
			@PathVariable("policyId") Long policyId)
			throws AssetNotFoundException {
		LOG.debug("Provider has received request to update asset:" + assetId
				+ " with policy id: " + policyId);
		MessageResponse em = new MessageResponse();
		int result = assetService.updatePolicy(policyId, assetId);
		if (result == 0) {
			throw new AssetNotFoundException("Asset not found");
		} else {
			em.setCode(HttpStatus.OK + "");
			em.setMessage("SUCCESS");
			em.setDescription("Asset " + assetId
					+ " is updated with policyId :" + policyId);
		}
		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);
	}

	/**
	 * This method will update the asset .
	 * @param id
	 * @param asset
	 * @return
	 */
	@RequestMapping(value = "/updateasset/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MessageResponse> updateAsset(
			@PathVariable("id") Long id, @RequestBody Asset asset) {

		LOG.debug("Provider has received request to edit asset with id: " + id);
		MessageResponse em = new MessageResponse();
		asset.setId(id);
		assetService.updateAsset(asset).toString();

		em.setCode(HttpStatus.OK + "");
		ErrorType et = new ErrorType();
		et.setDesc("Asset updated");
		et.setMsg("Asset updated");

		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);
	}

	/**
	 * This method will return all the delete eligible assets based on the
	 * policy sites.
	 * 
	 * @param storageId
	 * @return
	 * @throws ReplicaWSException
	 */
	@RequestMapping(value = "/deleteAssetRequest/{storageId}", method = RequestMethod.GET)
	public ResponseEntity<MessageResponse> findDeleteAssets(
			@PathVariable("storageId") Long storageId)
			throws ReplicaWSException {
		LOG.debug("findDeleteAssets storageId: " + storageId);
		List<Map<String, String>> dataList = assetService
				.findDeleteAssets(storageId);
		MessageResponse em = new MessageResponse();

		if (dataList != null && !dataList.isEmpty()) {
			em.setCode(HttpStatus.OK + "");
			em.setMessage("Success");
			em.setDescription("List of assets (full path) for deletion ");
		} else {
			em.setCode("0");
			em.setMessage("Success");
			em.setDescription("No jobs to perform.");
		}

		em.setDataList(dataList);
		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);
	}

	/**
	 * This method is used to update the status for the deleted asset instances.
	 * 
	 * @param deletedInstances
	 * @return
	 * @throws ReplicaWSException
	 */
	@RequestMapping(value = "/deleteAssetResponse", method = RequestMethod.POST)
	public ResponseEntity<MessageResponse> deleteAssetResponse(
			@RequestBody List<Map<String, String>> deletedInstances)
			throws ReplicaWSException {

		List<Integer> instanceIdsList = new ArrayList<Integer>();
		LOG.debug("DeleteAssetResponse deletedAssets: ");
		for (Map<String, String> instance : deletedInstances) {
			LOG.debug("Instance: " + instance.get("instanceId")
					+ " - DeleteStatus: " + instance.get("status"));
			if (instance.get("status").equals("DELETED")) {
				instanceIdsList
						.add(Integer.parseInt(instance.get("instanceId")));
			}
		}

		int returnVal = 0;
		String responseStatus = " ";
		if (instanceIdsList.size() != 0) {
			returnVal = assetService.updateDeletedInstances(instanceIdsList);
		}
		if (returnVal == 0 && instanceIdsList.size() != 0) {
			responseStatus = "Failed to update Asset instances";
		} else {
			responseStatus = returnVal
					+ " Asset instaces are updated successfully";
		}
		MessageResponse em = new MessageResponse();
		em.setCode(HttpStatus.OK + "");
		em.setMessage(responseStatus);
		em.setDescription(responseStatus);
		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);
	}

	/**
	 * This method will update the Delete date for the File assets.
	 * @param input
	 * @return
	 * @throws ReplicaWSException
	 */
	@RequestMapping(value = "/deleteFilesystem", method = RequestMethod.POST)
	public ResponseEntity<MessageResponse> findDeleteAssets(
			@RequestBody Map<String, String> input) throws ReplicaWSException {

		Long storageId = Long.parseLong(input.get("storageId"));
		String fileSysSnapshot = input.get("path");

		LOG.debug("DeleteFilesystem invoked for  STORAGE_ID: " + storageId
				+ ", LOG_FILE: " + fileSysSnapshot);

		Map<String, String> returnVal = assetService
				.deleteFilesystemCompare(storageId, fileSysSnapshot);

		MessageResponse em = new MessageResponse();

		if (returnVal.get("isPrimary").equals("true")) {
			em.setCode(HttpStatus.OK + "");
			em.setMessage("Success");
			em.setDescription(returnVal.get("updateCount")
					+ " - ASSETS marked with DELETE_DATE");
		} else {
			em.setCode(HttpStatus.NOT_ACCEPTABLE + "");
			em.setMessage("NOT A PRIMARY LOCATION");
			em.setDescription("THIS SCRIPT MUST RUN FROM ONLY PRIMARY LOCATIONS");
		}

		return new ResponseEntity<MessageResponse>(em, HttpStatus.OK);
	}
	
	/** 
	 * This method is used to find the assets which are require for verify policies
	 * 
	 * @param 
	 * @return
	 * @throws ReplicaWSException
	 */
	@RequestMapping(value = "/verifyPolicies", method = RequestMethod.GET)
	public ResponseEntity<List<MessageResponse>> verifyPolicies()throws ReplicaWSException {
		LOG.debug("verifyPolicies : ");
		List<MessageResponse> em = assetService.verifyPolicies();
		return new ResponseEntity<List<MessageResponse>>(em, HttpStatus.OK);
	}

}
