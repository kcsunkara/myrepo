package com.csc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.csc.doc.Emp;

public interface EmpService {

	Emp save(Emp emp);
	Emp findById(Long id);
	Iterable<Emp> findAll();
	
//	Page<Emp> findByDeptNo(String deptNo, PageRequest pageRequest);
}
