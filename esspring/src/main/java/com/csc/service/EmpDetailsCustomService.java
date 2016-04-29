package com.csc.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.csc.doc.Emp;
import com.csc.doc.EmpDetailsCustom;

public interface EmpDetailsCustomService {

	EmpDetailsCustom save(EmpDetailsCustom empDetails);
	EmpDetailsCustom findById(Long empno);
	Iterable<EmpDetailsCustom> findAll();
	Page<EmpDetailsCustom> findByFirstname(String firstname, PageRequest pageRequest);
	Page<EmpDetailsCustom> findByNameOrDept(Map<String, String> requestMap, Pageable pageable);
//	Page<Emp> findByDeptNo(String deptNo, PageRequest pageRequest);
}
