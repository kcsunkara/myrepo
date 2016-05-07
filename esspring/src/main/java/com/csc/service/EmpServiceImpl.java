package com.csc.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csc.doc.Dept;
import com.csc.doc.Emp;
import com.csc.jpa.repository.DeptRepositoryJPA;
import com.csc.jpa.repository.EmpRepositoryJPA;
import com.csc.repository.EmpRepository;

@Service
public class EmpServiceImpl implements EmpService {

	private static final Logger logger = Logger.getLogger(EmpServiceImpl.class);
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private EmpRepository empRepository;
	
	@Autowired
	private EmpRepositoryJPA jpaEmpRepository;
	
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
	@Transactional
	public Iterable<Emp> jpaFindAll() {
		Iterable<Emp> empListIterable =  jpaEmpRepository.findAll();
		return empListIterable;
	}

	@Override
	@Transactional
	public Emp jpaFindByID(Integer id) {
		return jpaEmpRepository.findOne(id);
	}

	@Override
	public String indexAllEmps() {
		Iterable<Emp> empListIterable = jpaFindAll();
		empRepository.save(empListIterable);
		return "Success";
	}

	@Override
	public Map<String, Page<Emp>> findByNameOrDept(Map<String, String> requestMap, Pageable pageable) {

		String searchText = requestMap.get("freeText");
		String retrieveFromDateStr = requestMap.get("retrieveFromDate");
		String retrieveToDateStr = requestMap.get("retrieveToDate");

		QueryBuilder queryBuilder7 = QueryBuilders.boolQuery()
				.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
				.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchText, "firstName", "lastName", "dept.deptName")
																.analyzer("standard");
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder)
				.withFilter(queryBuilder7)
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
				.withPageable(pageable).build();
		Page<Emp> matchingEntities = elasticsearchTemplate.queryForPage(searchQuery,Emp.class);
		
		Map<String, Page<Emp>> resultMap = new HashMap<String, Page<Emp>>();
		resultMap.put("Success", matchingEntities);
		return resultMap;
	}

}
