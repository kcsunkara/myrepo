package com.csc.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.csc.doc.Dept;
import com.csc.doc.Emp;

public interface OrgService {

	Emp save(Emp emp);
	Emp findById(Integer id);
	Iterable<Emp> findAll();
	Emp jpaFindByID(Integer id);
	Iterable<Emp> jpaFindAllEmps();
	Iterable<Dept> jpaFindAllDepts();
	String indexAllEmps();
	Map<String, Page<Emp>> findByNameOrDept(Map<String, String> requestMap, Pageable pageable);
}
