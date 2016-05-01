package com.csc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csc.doc.Dept;

@Repository
public interface DeptRepositoryJPA extends CrudRepository<Dept, Integer> {
	
}