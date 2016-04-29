package com.csc.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csc.doc.EmpDetailsCustom;
import com.csc.service.EmpDetailsCustomService;

@RestController 
@RequestMapping("/")
public class EmpDetailsCustomController {
	
	private static final Logger logger = Logger.getLogger(EmpDetailsCustomController.class);
	
	@Autowired
	EmpDetailsCustomService empDetailsCustomService;
	
	@RequestMapping(value = "/findEmpDetailsById", method = RequestMethod.GET)
	public @ResponseBody EmpDetailsCustom findEmpDetailsById(@RequestParam Long id) {
		return empDetailsCustomService.findById(id);
	}
	
	@RequestMapping(value = "/findEmpDetailsByFirstName", method = RequestMethod.GET)
	public @ResponseBody Page<EmpDetailsCustom> findEmpDetailsByFirstName(@RequestParam String firstname) {
		return empDetailsCustomService.findByFirstname(firstname, new PageRequest(0, 3));
	}
	
	@RequestMapping(value = "/findAllEmpDetails", method = RequestMethod.GET)
	public @ResponseBody Iterable<EmpDetailsCustom> findAllEmpDetails() {
		return empDetailsCustomService.findAll();
	}
	
	@RequestMapping(value = "/saveEmpDetails", method = RequestMethod.POST)
	public @ResponseBody EmpDetailsCustom saveEmpDetails(@RequestBody EmpDetailsCustom empDetailsCustom) {
		return empDetailsCustomService.save(empDetailsCustom);
	}
	
	@RequestMapping(value = "/findByNameOrDept", method = RequestMethod.POST)
	public @ResponseBody Page<EmpDetailsCustom> findByNameOrDept(@RequestBody Map<String, String> requestMap) {
		return empDetailsCustomService.findByNameOrDept(requestMap, new PageRequest(0, 3));
	}

}
