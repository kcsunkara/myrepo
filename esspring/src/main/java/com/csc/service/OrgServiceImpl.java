package com.csc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		logger.info("OrgServiceImpl.findById() - for ID: " + id);
		return esEmpRepository.findOne(id);
	}

	@Override
	public Iterable<Emp> findAll() {
		return esEmpRepository.findAll();
	}

	@Override
	@Transactional
	public Emp jpaFindByID(Integer id) {
		logger.info("OrgServiceImpl.jpaFindByID() - for ID: " + id);
		return jpaEmpRepository.findOne(id);
	}

	@Override
	@Transactional
	public Iterable<Emp> jpaFindAllEmps() {
		return jpaEmpRepository.findAll();
	}

	@Override
	@Transactional
	public Iterable<Dept> jpaFindAllDepts() {
		return jpaDeptRepository.findAll();
	}

	@Override
	public String indexAllEmps() {
		logger.info("OrgServiceImpl.indexAllEmps() - Start Time: " + Calendar.getInstance().getTime());

		Iterable<Emp> empListIterable = jpaFindAllEmps();
		logger.info("OrgServiceImpl.indexAllEmps() - Loading all EMP's completed - at: " + Calendar.getInstance().getTime());

		Iterable<Dept> deptListIterable = jpaFindAllDepts();
		logger.info("OrgServiceImpl.indexAllEmps() - Loading all Dept's completed - at: " + Calendar.getInstance().getTime());

		esEmpRepository.save(empListIterable);
		logger.info("OrgServiceImpl.indexAllEmps() - Indexing all Emp's completed - at: " + Calendar.getInstance().getTime());

		esDeptRepository.save(deptListIterable);
		logger.info("OrgServiceImpl.indexAllEmps() - Indexing all Dept's completed - at: " + Calendar.getInstance().getTime());

		logger.info("OrgServiceImpl.indexAllEmps() - End Time: " + Calendar.getInstance().getTime());
		return "Success";
	}

	@Override
	public Map<String, Page<Emp>> findByNameOrDept(Map<String, String> requestMap, Pageable pageable) {
		logger.info("OrgServiceImpl.findByNameOrDept() - for request - " + requestMap + " - for Paging - " + pageable);

		String searchText = requestMap.get("freeText");
		String retrieveFromDateStr = requestMap.get("retrieveFromDate");
		String retrieveToDateStr = requestMap.get("retrieveToDate");

		QueryBuilder queryBuilderDept = QueryBuilders.matchQuery("deptName", searchText).analyzer("standard");
		SearchQuery searchQueryDept = new NativeSearchQueryBuilder().withQuery(queryBuilderDept).build();
		List<String> matchingDeptList = elasticsearchTemplate.queryForIds(searchQueryDept);

		logger.info("OrgServiceImpl.findByNameOrDept() - Matching Dept ID's for input text("+ searchText + "): " + matchingDeptList);

		QueryBuilder queryBuilderFilter = null;
		if (matchingDeptList.size() > 0) {
			queryBuilderFilter = QueryBuilders.boolQuery()
					.must(QueryBuilders.termsQuery("deptNo", matchingDeptList))
					.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
					.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
		} else {
			queryBuilderFilter = QueryBuilders.boolQuery()
					.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
					.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
		}

		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchText, "firstName", "lastName").analyzer("standard");

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder)
				.withFilter(queryBuilderFilter)
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
				.withPageable(pageable).build();
		Page<Emp> matchingEntities = elasticsearchTemplate.queryForPage(searchQuery, Emp.class);

		logger.info("OrgServiceImpl.findByNameOrDept() - No. of matching Emp's for total search criteria: " + matchingEntities.getTotalElements());

		Map<String, Page<Emp>> resultMap = new HashMap<String, Page<Emp>>();
		resultMap.put("Success", matchingEntities);
		return resultMap;
	}

	@Override
	public Map<String, Page<Emp>> findByNameOrDept_2(Map<String, String> requestMap, Pageable pageable) {
		logger.info("OrgServiceImpl.findByNameOrDept_2() - for request - " + requestMap + " - for Paging - " + pageable);

		String searchText = requestMap.get("freeText");
		String retrieveFromDateStr = requestMap.get("retrieveFromDate");
		String retrieveToDateStr = requestMap.get("retrieveToDate");

		QueryBuilder queryBuilderDept = QueryBuilders.matchQuery("deptName", searchText).analyzer("standard");
		SearchQuery searchQueryDept = new NativeSearchQueryBuilder().withQuery(queryBuilderDept).build();
		List<String> matchingDeptList = elasticsearchTemplate.queryForIds(searchQueryDept);
		logger.info("OrgServiceImpl.findByNameOrDept_2() - Matching Dept ID's for input text("+ searchText + "): " + matchingDeptList);

		QueryBuilder queryBuilderFilter = null;
		if (matchingDeptList.size() > 0) {
			queryBuilderFilter = QueryBuilders.boolQuery()
					.must(QueryBuilders.termsQuery("deptNo", matchingDeptList))
					.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
					.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
		} else {
			queryBuilderFilter = QueryBuilders.boolQuery()
					.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
					.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
		}

		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchText, "firstName", "lastName").analyzer("standard");

		SearchQuery searchQueryForEmps = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder).build();
		List<String> matchingEmpIdList = elasticsearchTemplate.queryForIds(searchQueryForEmps);
		logger.info("OrgServiceImpl.findByNameOrDept_2() - Matching Emp ID's for input text("+ searchText + "): " + matchingEmpIdList);
		
		SearchQuery searchQuery = null;
		if(matchingEmpIdList.size() > 0) {
			searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder)
					.withFilter(queryBuilderFilter)
					.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
					.withPageable(pageable).build();
		} else {
			searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termsQuery("deptNo", matchingDeptList)).build();
		}
		
		
		Page<Emp> matchingEntities = elasticsearchTemplate.queryForPage(searchQuery, Emp.class);

		logger.info("OrgServiceImpl.findByNameOrDept_2() - No. of matching Emp's for total search criteria: " + matchingEntities.getTotalElements());

		Map<String, Page<Emp>> resultMap = new HashMap<String, Page<Emp>>();
		resultMap.put("Success", matchingEntities);
		return resultMap;
	}
	
	@Override
	public Map<String, Page<Emp>> findByNameOrDept_Manual(
			Map<String, String> requestMap, Pageable pageable) {

		String searchText = requestMap.get("freeText");
		String retrieveFromDateStr = requestMap.get("retrieveFromDate");
		String retrieveToDateStr = requestMap.get("retrieveToDate");

		StringTokenizer tokenizer = new StringTokenizer(searchText);
		String token1 = "";
		String token2 = "";
		if (tokenizer.countTokens() == 2) {
			token1 = tokenizer.nextToken();
			token2 = tokenizer.nextToken();
		} else {
			token1 = tokenizer.nextToken();
		}
		boolean isName = isFirstStringEmpName(token1);
		String empName = "";
		String deptName = "";

		if (isName && !token1.isEmpty() && !token2.isEmpty()) {
			empName = token1;
			deptName = token2;
		} else if (!isName && !token1.isEmpty() && !token2.isEmpty()) {
			empName = token2;
			deptName = token1;
		} else if (isName && token2.isEmpty()) {
			empName = token1;
		} else if (!isName && token2.isEmpty()) {
			deptName = token1;
		}

		List<String> deptNumbers = new ArrayList<String>();
		if (!deptName.isEmpty()) {
			deptNumbers = searchDocumentDept(deptName);
		}
		Page<Emp> matchingEntities = searchDocument(empName, deptNumbers,
				retrieveFromDateStr, retrieveToDateStr);

		Map<String, Page<Emp>> resultMap = new HashMap<String, Page<Emp>>();
		resultMap.put("Success", matchingEntities);
		return resultMap;
	}

	public boolean isFirstStringEmpName(String stringToken) {
		List<String> result = new ArrayList<String>();
		QueryBuilder queryBuilder = QueryBuilders
				.queryStringQuery("*" + stringToken + "*").analyzer("standard")
				.analyzeWildcard(true).field("firstName").field("lastName");
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder).withPageable(new PageRequest(0, 10))
				.build();

		result = elasticsearchTemplate.queryForIds(searchQuery);
		return result.size() > 0;
	}

	public List<String> searchDocumentDept(String deptName) {
		List<String> result = new ArrayList<String>();
		QueryBuilder queryBuilder = QueryBuilders
				.queryStringQuery("*" + deptName + "*").analyzer("standard").analyzeWildcard(true).field("deptName");
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder).withPageable(new PageRequest(0, 10))
				.build();
		result = elasticsearchTemplate.queryForIds(searchQuery);
		return result;
	}

	public  Page<Emp> searchDocument(String name, List<String> deptNumbers, String retrieveFromDateStr, String retrieveToDateStr){
		
		QueryBuilder queryBuilderWithDept = QueryBuilders.boolQuery()
				.must(QueryBuilders.queryStringQuery("*" + name + "*").analyzer("standard").analyzeWildcard(true).field("firstName").field("lastName"))
				.must(QueryBuilders.termsQuery("deptNo", deptNumbers))
				.must(QueryBuilders.rangeQuery("salary.fromDate").from(retrieveFromDateStr))
				.must(QueryBuilders.rangeQuery("salary.toDate").to(retrieveToDateStr));
		
		QueryBuilder queryBuilderWithoutDept = QueryBuilders.boolQuery()
				.must(QueryBuilders.queryStringQuery("*" + name + "*").analyzer("standard").analyzeWildcard(true).field("firstName").field("lastName"))
				.must(QueryBuilders.rangeQuery("salary.fromDate").from(retrieveFromDateStr))
				.must(QueryBuilders.rangeQuery("salary.toDate").to(retrieveToDateStr));
		
		SearchQuery searchQuery = null;
		if(deptNumbers.size() > 0) {
			searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilderWithDept)
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
				.withPageable(new PageRequest(0, 10))
				.build();
		} else {
			searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilderWithoutDept)
    				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
    				.withPageable(new PageRequest(0, 10))
    				.build();	
		}
		Page<Emp> result = elasticsearchTemplate.queryForPage(searchQuery,Emp.class);
		logger.info("OrgServiceImpl.findByNameOrDept_Manual() - Matching Dept ID's: " + deptNumbers);
		logger.info("OrgServiceImpl.findByNameOrDept_Manual() - No. of matching Emp's for total search criteria: " + result.getTotalElements());
		return result;
    }
}
