package com.csc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.csc.doc.EmpDetailsCustom;

@Repository
public interface EmpDetailsCustomRepository extends ElasticsearchRepository<EmpDetailsCustom, Long> {
	
	Page<EmpDetailsCustom> findByFirstname(String firstname, Pageable pageable);
//	Page<EmpDetailsCustom> findByNameOrDept(String text, Pageable pageable);
//	Page<Emp> findOByDeptNo(String deptNo, Pageable pageable);

}
