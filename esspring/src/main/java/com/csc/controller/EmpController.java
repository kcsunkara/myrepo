package com.csc.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csc.doc.Emp;
import com.csc.enums.JsonMessageType;
import com.csc.service.OrgService;
import com.csc.validate.JsonValidator;

@RestController
@RequestMapping("/")
public class EmpController {

	private static final Logger logger = Logger.getLogger(EmpController.class);

	@Autowired
	OrgService orgService;

	@Autowired
	JsonValidator jsonValidator;

	@RequestMapping("/")
	public String defaultView() {
		return "forward:/index.html";
	}

	@RequestMapping(value = "/findEmpById", method = RequestMethod.GET)
	public @ResponseBody Emp findEmpById(@RequestParam Integer id) {
		logger.debug("EmpController.findEmpById() - for ID: "+id);
		return orgService.findById(id);
	}

	@RequestMapping(value = "/findAllEmps", method = RequestMethod.GET)
	public @ResponseBody Iterable<Emp> findAll() {
		return orgService.findAll();
	}

	@RequestMapping(value = "/jpaFindAllEmps", method = RequestMethod.GET)
	public @ResponseBody Iterable<Emp> jpaFindAllEmps() {
		return orgService.jpaFindAllEmps();
	}

	@RequestMapping(value = "/jpaFindEmpById", method = RequestMethod.GET)
	public @ResponseBody Emp jpaFindEmpById(@RequestParam Integer id) {
		logger.debug("EmpController.jpaFindEmpById() - for ID: "+id);
		return orgService.jpaFindByID(id);
	}

	@RequestMapping(value = "/saveEmp", method = RequestMethod.POST)
	public @ResponseBody Emp saveEmp(@RequestBody Emp emp) {
		return orgService.save(emp);
	}

	@RequestMapping(value = "/indexAllEmps", method = RequestMethod.GET)
	public @ResponseBody String indexAllEmps() {
		logger.debug("EmpController.indexAllEmps() - Start Time: " + Calendar.getInstance().getTime());
		String serviceStatus =  orgService.indexAllEmps();
		logger.debug("EmpController.indexAllEmps() - End Time: " + Calendar.getInstance().getTime());
		return serviceStatus;
	}

	@RequestMapping(value = "/findByNameOrDept", method = RequestMethod.POST)
	public @ResponseBody Map<String, Page<Emp>> findByNameOrDept(
			@RequestParam Integer pageNo, @RequestParam Integer maxRecords,
			@RequestBody Map<String, String> requestMap) {
		logger.info("EmpController.findByNameOrDept() - for request: " + requestMap + " - pageNo: " + pageNo + " - maxRecords: " + maxRecords);
		List ls = new ArrayList();
		JSONObject obj = new JSONObject();
		obj.put("freeText", requestMap.get("freeText"));
		obj.put("retrieveFromDate", requestMap.get("retrieveFromDate"));
		obj.put("retrieveToDate", requestMap.get("retrieveToDate"));

		if(jsonValidator.validate(obj,JsonMessageType.REQUEST)) {
			return orgService.findByNameOrDept(requestMap, new PageRequest(pageNo, maxRecords));
		} else {
			Map<String, Page<Emp>> resultMap = new HashMap<String, Page<Emp>>();
			String message = "Request JSON is not valid. Required Format: " + "{" +
			    "freeText:" + "<name dept>, " +
			    "retrieveFromDate:" + "dd/MMM/yyyy, " +
			    "retrieveToDate:" + "dd/MMM/yyyy " + "}";
			resultMap.put(message, null);
			return resultMap;
		}
	}
	
	@RequestMapping(value = "/findByNameOrDept_Manual", method = RequestMethod.POST)
	public @ResponseBody Map<String, Page<Emp>> findByNameOrDept_manual(
			@RequestParam Integer pageNo, @RequestParam Integer maxRecords,
			@RequestBody Map<String, String> requestMap) {
		logger.info("EmpController.findByNameOrDept_manual() - for request: " + requestMap + " - pageNo: " + pageNo + " - maxRecords: " + maxRecords);
		List ls = new ArrayList();
		JSONObject obj = new JSONObject();
		obj.put("freeText", requestMap.get("freeText"));
		obj.put("retrieveFromDate", requestMap.get("retrieveFromDate"));
		obj.put("retrieveToDate", requestMap.get("retrieveToDate"));

		if(jsonValidator.validate(obj,JsonMessageType.REQUEST)) {
			return orgService.findByNameOrDept_Manual(requestMap, new PageRequest(pageNo, maxRecords));
		} else {
			Map<String, Page<Emp>> resultMap = new HashMap<String, Page<Emp>>();
			String message = "Request JSON is not valid. Required Format: " + "{" +
			    "freeText:" + "<name dept>, " +
			    "retrieveFromDate:" + "dd/MMM/yyyy, " +
			    "retrieveToDate:" + "dd/MMM/yyyy " + "}";
			resultMap.put(message, null);
			return resultMap;
		}
	}

}
