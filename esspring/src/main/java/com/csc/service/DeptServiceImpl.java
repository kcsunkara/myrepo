package com.csc.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.csc.doc.Dept;
import com.csc.repository.DeptRepository;

@Service
public class DeptServiceImpl implements DeptService {

	private static final Logger logger = Logger.getLogger(DeptServiceImpl.class);
	
	@Autowired
	private DeptRepository deptRepository;

	@Override
	public Dept save(Dept dept) {
		return deptRepository.save(dept);
	}

	@Override
	public Dept findById(Long id) {
		return deptRepository.findOne(id);
	}

	@Override
	public Iterable<Dept> findAll() {
		return deptRepository.findAll();
	}

	/*@Override
	public Page<Dept> findByEmpListName(String empName, PageRequest pageRequest) {
		return deptRepository.findByEmpListName(empName, pageRequest);
	}*/


}
