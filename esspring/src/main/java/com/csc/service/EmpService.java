package com.csc.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.csc.doc.Emp;

public interface EmpService {

	Emp save(Emp emp);
	Emp findById(Integer id);
	Iterable<Emp> findAll();
	
//	Page<Emp> findByDeptNo(String deptNo, PageRequest pageRequest);
	
	Emp jpaFindByID(Integer id);
	Iterable<Emp> jpaFindAll();
	
	Emp save1();
	
	String indexAllEmps();
	
	Page<Emp> findByNameOrDept(Map<String, String> requestMap, Pageable pageable);
}
