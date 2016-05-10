package com.csc.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc.doc.Emp;

@Repository
public interface EmpRepositoryJPA extends JpaRepository<Emp, Integer> {
	
}
