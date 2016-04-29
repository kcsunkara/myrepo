package com.csc.controller;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

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

import com.csc.doc.Dept;
import com.csc.doc.Emp;
import com.csc.service.DeptService;

@RestController 
@RequestMapping("/")
public class DeptController {
	
	private static final Logger logger = Logger.getLogger(DeptController.class);
	
	@Autowired
	DeptService deptService;
	
	@RequestMapping(value = "/findDeptById", method = RequestMethod.GET)
	public @ResponseBody Dept findById(@RequestParam Long id) {
		return deptService.findById(id);
	}
	
	@RequestMapping(value = "/findAllDepts", method = RequestMethod.GET)
	public @ResponseBody Iterable<Dept> findAllDepts() {
		return deptService.findAll();
	}
	
	@RequestMapping(value = "/saveDept", method = RequestMethod.POST)
	public @ResponseBody Dept saveDept(@RequestBody Dept dept) {
		return deptService.save(dept);
	}
	
	/*@RequestMapping(value = "/findDeptByEmpName", method = RequestMethod.GET)
	public @ResponseBody Page<Dept> saveDept(@RequestParam String empName) {
		return deptService.findByEmpListName(empName, new PageRequest(0, 10));
	}*/
	
	/*@RequestMapping(value = "/saveDept1", method = RequestMethod.GET)
	public @ResponseBody Dept saveDept1() {
		logger.info("DeptController.saveDept1()");
		Dept dept = new Dept();
		dept.setId(2);
		dept.setName("Java");
		
		Emp emp = new Emp();
		emp.setId(101);
		emp.setName("Krishna");
		emp.setFromDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		
		Emp emp2 = new Emp();
		emp2.setId(102);
		emp2.setName("Krishna Sunkara");
		emp2.setFromDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		
		dept.setEmpList(Arrays.asList(emp, emp2));
		return deptService.save(dept);
	}*/

}
