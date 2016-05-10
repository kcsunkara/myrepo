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
import com.csc.repository.DeptRepository;
import com.csc.repository.EmpRepository;

@Service
public class OrgServiceImpl implements OrgService {

	private static final Logger logger = Logger.getLogger(OrgServiceImpl.class);
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private EmpRepository esEmpRepository;
	
	@Autowired
	private DeptRepository esDeptRepository;
	
	@Autowired
	private EmpRepositoryJPA jpaEmpRepository;
	
	@Autowired
	private DeptRepositoryJPA jpaDeptRepository;
	
	@Override
	public Emp save(Emp emp) {
		logger.debug("EmpServiceImpl.save() --> " + emp);
		return esEmpRepository.save(emp);
	}

	@Override
	public Emp findById(Integer id) {
		return esEmpRepository.findOne(id);
	}

	@Override
	public Iterable<Emp> findAll() {
		return esEmpRepository.findAll();
	}

	@Override
	@Transactional
	public Emp jpaFindByID(Integer id) {
		return jpaEmpRepository.findOne(id);
	}

	@Override
	public String indexAllEmps() {
		Iterable<Emp> empListIterable = jpaFindAllEmps();
		Iterable<Dept> deptListIterable = jpaFindAllDepts();
		esEmpRepository.save(empListIterable);
		esDeptRepository.save(deptListIterable);
		return "Success";
	}

	@Override
	public Map<String, Page<Emp>> findByNameOrDept(Map<String, String> requestMap, Pageable pageable) {

		String searchText = requestMap.get("freeText");
		String retrieveFromDateStr = requestMap.get("retrieveFromDate");
		String retrieveToDateStr = requestMap.get("retrieveToDate");

		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
				.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchText, "firstName", "lastName")
																.analyzer("ngram_analyzer");
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder)
				.withFilter(queryBuilder)
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
				.withPageable(pageable).build();
		Page<Emp> matchingEntities = elasticsearchTemplate.queryForPage(searchQuery,Emp.class);
		
		Map<String, Page<Emp>> resultMap = new HashMap<String, Page<Emp>>();
		resultMap.put("Success", matchingEntities);
		return resultMap;
	}

	@Override
	public Iterable<Emp> jpaFindAllEmps() {
		return jpaEmpRepository.findAll();
	}

	@Override
	public Iterable<Dept> jpaFindAllDepts() {
		return jpaDeptRepository.findAll();
	}

}
