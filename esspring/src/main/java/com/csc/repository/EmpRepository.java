package com.csc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.csc.doc.Emp;

public interface EmpRepository extends ElasticsearchRepository<Emp, Long> {
	
//	Page<Emp> findByDeptNo(String deptNo, Pageable pageable);

}