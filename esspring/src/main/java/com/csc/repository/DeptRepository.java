package com.csc.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.csc.doc.Dept;

public interface DeptRepository extends ElasticsearchRepository<Dept, Long> {
//	Page<Dept> findByEmpListName(String name, Pageable pageable);
}
