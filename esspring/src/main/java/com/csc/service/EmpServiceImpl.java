package com.csc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc.doc.Dept;
import com.csc.doc.Emp;
import com.csc.doc.Salaries;
import com.csc.repository.DeptRepositoryJPA;
import com.csc.repository.EmpRepository;
import com.csc.repository.EmpRepositoryJPA;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {

	private static final Logger logger = Logger.getLogger(EmpServiceImpl.class);
	
	@Autowired
	private EmpRepository empRepository;
	
	@Autowired
	private EmpRepositoryJPA jpaEmpRepository;
	
	@Autowired
	private DeptRepositoryJPA jpaDeptRepository;
	
	@Override
	public Emp save(Emp emp) {
		logger.debug("EmpServiceImpl.save() --> " + emp);
		return empRepository.save(emp);
	}

	@Override
	public Emp findById(Integer id) {
		return empRepository.findOne(id);
	}

	@Override
	public Iterable<Emp> findAll() {
		return empRepository.findAll();
	}

	@Override
	public Iterable<Emp> jpaFindAll() {
		Iterable<Emp> empListIterable =  jpaEmpRepository.findAll();
		return empListIterable;
	}

	@Override
	public Emp jpaFindByID(Integer id) {
		return jpaEmpRepository.findOne(id);
	}

	@Override
	public Emp save1() {
		Dept dept = jpaDeptRepository.findOne(70);
		Emp emp = new Emp();
		emp.setId(10413);
		emp.setFirstName("Krishna");
		emp.setDept(dept);
		jpaEmpRepository.save(emp);
		return emp;
	}

	/*@Override
	public Page<Emp> findByDeptNo(String deptNo, PageRequest pageRequest) {
		return empRepository.findByDeptNo(deptNo, pageRequest);
	}*/

}
