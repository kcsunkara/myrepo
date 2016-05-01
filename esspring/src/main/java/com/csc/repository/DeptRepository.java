package com.csc.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.csc.doc.Dept;

@Repository
public interface DeptRepository extends ElasticsearchRepository<Dept, Integer> {
//	Page<Dept> findByEmpListName(String name, Pageable pageable);
}
