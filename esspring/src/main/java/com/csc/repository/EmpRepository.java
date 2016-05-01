package com.csc.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.csc.doc.Emp;

@Repository
public interface EmpRepository extends ElasticsearchRepository<Emp, Integer> {
	
//	Page<Emp> findByDeptNo(String deptNo, Pageable pageable);

}
