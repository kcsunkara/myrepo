package com.csc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.csc.doc.Dept;

public interface DeptService {

	Dept save(Dept dept);
	Dept findById(Long id);
	Iterable<Dept> findAll();
	
	Page<Dept> findByEmpListName(String empName, PageRequest pageRequest);
}
