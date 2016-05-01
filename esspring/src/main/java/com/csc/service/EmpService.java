package com.csc.service;

import com.csc.doc.Emp;

public interface EmpService {

	Emp save(Emp emp);
	Emp findById(Integer id);
	Iterable<Emp> findAll();
	
//	Page<Emp> findByDeptNo(String deptNo, PageRequest pageRequest);
	
	Emp jpaFindByID(Integer id);
	Iterable<Emp> jpaFindAll();
	
	Emp save1();
	
}
