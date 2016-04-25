package com.csc.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.csc.doc.Emp;
import com.csc.repository.EmpRepository;

@Service
public class EmpServiceImpl implements EmpService {

	private static final Logger logger = Logger.getLogger(EmpServiceImpl.class);
	
	@Autowired
	private EmpRepository empRepository;
	
	@Override
	public Emp save(Emp emp) {
		logger.debug("EmpServiceImpl.save() --> " + emp);
		return empRepository.save(emp);
	}

	@Override
	public Emp findById(Long id) {
		return empRepository.findOne(id);
	}

	@Override
	public Iterable<Emp> findAll() {
		return empRepository.findAll();
	}

	@Override
	public Page<Emp> findByDeptId(Long deptId, PageRequest pageRequest) {
		return empRepository.findByDeptId(deptId, pageRequest);
	}

	@Override
	public Page<Emp> findByDeptName(String deptName, PageRequest pageRequest) {
		return empRepository.findByDeptName(deptName, pageRequest);
	}

}
