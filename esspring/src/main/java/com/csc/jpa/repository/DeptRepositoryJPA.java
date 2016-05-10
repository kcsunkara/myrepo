package com.csc.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csc.doc.Dept;

@Repository
public interface DeptRepositoryJPA extends JpaRepository<Dept, Integer> {
	
}
