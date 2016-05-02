package com.csc.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csc.doc.Emp;

@Repository
public interface EmpRepositoryJPA extends CrudRepository<Emp, Integer> {
	
}
