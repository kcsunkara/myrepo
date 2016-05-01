package com.csc.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc.doc.Dept;
import com.csc.repository.DeptRepository;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {

	private static final Logger logger = Logger.getLogger(DeptServiceImpl.class);
	
	@Autowired
	private DeptRepository deptRepository;

	@Override
	public Dept save(Dept dept) {
		return deptRepository.save(dept);
	}

	@Override
	public Dept findById(Integer id) {
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
