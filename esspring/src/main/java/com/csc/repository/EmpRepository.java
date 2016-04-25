package com.csc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.csc.doc.Emp;

public interface EmpRepository extends ElasticsearchRepository<Emp, Long> {
	
	Page<Emp> findByDeptId(Long deptId, Pageable pageable);
	Page<Emp> findByDeptName(String deptName, Pageable pageable);

}
