package com.csc.service;

import com.csc.doc.Dept;

public interface DeptService {

	Dept save(Dept dept);
	Dept findById(Integer id);
	Iterable<Dept> findAll();
	
//	Page<Dept> findByEmpListName(String empName, PageRequest pageRequest);
}
