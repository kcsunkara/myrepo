package com.csc.controller;

import java.util.ArrayList;
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
import com.csc.service.EmpService;
import com.csc.validate.JsonValidator;

@RestController
@RequestMapping("/")
public class EmpController {

	private static final Logger logger = Logger.getLogger(EmpController.class);

	@Autowired
	EmpService empService;

	@Autowired
	JsonValidator jsonValidator;

	@RequestMapping("/")
	public String defaultView() {
		return "forward:/index.html";
	}

	@RequestMapping(value = "/findEmpById", method = RequestMethod.GET)
	public @ResponseBody Emp findById(@RequestParam Integer id) {
		return empService.findById(id);
	}

	@RequestMapping(value = "/findAllEmps", method = RequestMethod.GET)
	public @ResponseBody Iterable<Emp> findAll() {
		return empService.findAll();
	}

	@RequestMapping(value = "/jpaFindAllEmps", method = RequestMethod.GET)
	public @ResponseBody Iterable<Emp> jpaFindAllEmps() {
		return empService.jpaFindAll();
	}

	@RequestMapping(value = "/jpaFindEmpById", method = RequestMethod.GET)
	public @ResponseBody Emp jpaFindEmpById(@RequestParam Integer id) {
		return empService.jpaFindByID(id);
	}

	@RequestMapping(value = "/saveEmp", method = RequestMethod.POST)
	public @ResponseBody Emp saveEmp(@RequestBody Emp emp) {
		return empService.save(emp);
	}

	@RequestMapping(value = "/indexAllEmps", method = RequestMethod.GET)
	public @ResponseBody String indexAllEmps() {
		return empService.indexAllEmps();
	}

	@RequestMapping(value = "/findByNameOrDept", method = RequestMethod.POST)
	public @ResponseBody Page<Emp> findByNameOrDept(
			@RequestParam Integer pageNo, @RequestParam Integer maxRecords,
			@RequestBody Map<String, String> requestMap) {

		List ls = new ArrayList();
		JSONObject obj = new JSONObject();
		obj.put("freeText", requestMap.get("freeText"));
		obj.put("retrieveFromDate", requestMap.get("retrieveFromDate"));
		obj.put("retrieveToDate", requestMap.get("retrieveToDate"));

		if (jsonValidator.validate(obj)) {
			return empService.findByNameOrDept(requestMap, new PageRequest(pageNo, maxRecords));
		}
		return null;
	}

	/*
	 * @RequestMapping(value = "/findEmpByDeptNo", method = RequestMethod.GET)
	 * public @ResponseBody Page<Emp> findEmpByDeptNo(@RequestParam String
	 * deptNo) { logger.info("EmpController.findEmpByDeptNo() --> deptNo = " +
	 * deptNo); Page<Emp> pages = null; pages = empService.findByDeptNo(deptNo,
	 * new PageRequest(0, 10)); return pages; }
	 */

	@RequestMapping(value = "/saveEmp1", method = RequestMethod.GET)
	public @ResponseBody Emp saveEmp1() {
		logger.info("EmpController.saveEmp1()");

		return empService.save1();
	}

}
