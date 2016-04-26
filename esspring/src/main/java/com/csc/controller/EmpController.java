package com.csc.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

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
import com.csc.service.EmpService;

@RestController 
@RequestMapping("/")
public class EmpController {
	
	private static final Logger logger = Logger.getLogger(EmpController.class);
	
	@Autowired
	EmpService empService;
	
	@RequestMapping(value = "/findEmpById", method = RequestMethod.GET)
	public @ResponseBody Emp findById(@RequestParam Long id) {
		return empService.findById(id);
	}
	
	@RequestMapping(value = "/findAllEmps", method = RequestMethod.GET)
	public @ResponseBody Iterable<Emp> findById() {
		return empService.findAll();
	}
	
	@RequestMapping(value = "/saveEmp", method = RequestMethod.POST)
	public @ResponseBody Emp saveEmp(@RequestBody Emp emp) {
		if(emp.getFromDate() == null) {
			emp.setFromDate(new Date());
		}
		return empService.save(emp);
	}
	
	/*@RequestMapping(value = "/findEmpByDeptNo", method = RequestMethod.GET)
	public @ResponseBody Page<Emp> findEmpByDeptNo(@RequestParam String deptNo) {
		logger.info("EmpController.findEmpByDeptNo() --> deptNo = " + deptNo);
		Page<Emp> pages = null;
		pages = empService.findByDeptNo(deptNo, new PageRequest(0, 10));
		return pages;
	}*/
	
	@RequestMapping(value = "/saveEmp1", method = RequestMethod.GET)
	public @ResponseBody Emp saveEmp1() {
		logger.info("EmpController.saveEmp()");
		Dept dept = new Dept();
		dept.setId(1);
		dept.setName("Technology");
		
		Emp emp = new Emp();
		emp.setId(101);
		emp.setName("Krishna");
		emp.setFromDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
		
		return empService.save(emp);
	}

}
