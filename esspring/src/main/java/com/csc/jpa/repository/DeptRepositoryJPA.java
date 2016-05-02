package com.csc.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csc.doc.Dept;

@Repository
public interface DeptRepositoryJPA extends CrudRepository<Dept, Integer> {
	
}
