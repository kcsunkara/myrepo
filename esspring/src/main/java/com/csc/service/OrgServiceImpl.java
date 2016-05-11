package com.csc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
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
		
		StringTokenizer tokenizer = new StringTokenizer(searchText);
		String token1 = "";
		String token2 = "";
		if(tokenizer.countTokens() == 2) {
			token1 = tokenizer.nextToken();
			token2 = tokenizer.nextToken();
		} else {
			token1 = tokenizer.nextToken();
		}
		boolean isName = isFirstStringEmpName(token1);
		String empName = "";
		String deptName = "";
		
		if(isName && !token1.isEmpty() && !token2.isEmpty()) {
			empName = token1; 
			deptName = token2;
		} else if(!isName && !token1.isEmpty() && !token2.isEmpty()){
			empName = token2; 
			deptName = token1;
		} else if(isName && token2.isEmpty()){
			empName = token1;
		} else if(!isName && token2.isEmpty()) {
			deptName = token1;
		}
		
		List<String> deptNumbers = new ArrayList<String>();
		if(!deptName.isEmpty()) { 
			deptNumbers = searchDocumentDept(deptName);
		}
		Page<Emp> matchingEntities = searchDocument(empName, deptNumbers, retrieveFromDateStr, retrieveToDateStr);
		

		/*QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
				.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchText, "firstName", "lastName")
																.analyzer("ngram_analyzer");
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder)
				.withFilter(queryBuilder)
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
				.withPageable(pageable).build();
		Page<Emp> matchingEntities = elasticsearchTemplate.queryForPage(searchQuery,Emp.class);*/
		
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
	
	public boolean isFirstStringEmpName(String stringToken) {
		List<String> result = new ArrayList<String>();
		QueryBuilder queryBuilder = QueryBuilders
				.queryStringQuery("*" + stringToken + "*").analyzeWildcard(true)
				.field("firstName").field("lastName");
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder)
				.withPageable(new PageRequest(0, 10)).build();

		result = elasticsearchTemplate.queryForIds(searchQuery);
		return result.size() > 0;
	}
	
	public List<String> searchDocumentDept(String deptName) {
    	List<String> result = new ArrayList<String>(); 
			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("*" + deptName + "*").analyzeWildcard(true).field("deptName");
			SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
					.withPageable(new PageRequest(0, 10))
					.build();
			result = elasticsearchTemplate.queryForIds(searchQuery);
		return result;
    } 
	
	public  Page<Emp> searchDocument(String name, List<String> deptNumbers, String retrieveFromDateStr, String retrieveToDateStr){  
   			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("*" + name + "*").analyzeWildcard(true).field("firstName").field("lastName");
   			QueryBuilder dateRangeQueryBuilder = QueryBuilders.boolQuery()
   					.must(QueryBuilders.rangeQuery("salary.fromDate").gte(retrieveFromDateStr).lte(retrieveToDateStr))
   					.must(QueryBuilders.rangeQuery("salary.toDate").gte(retrieveFromDateStr).lte(retrieveToDateStr));
    		
   			SearchQuery searchQuery = null;
			if(deptNumbers.size() > 0) {
				searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
   					.withFilter(QueryBuilders.termsQuery("deptNo",  deptNumbers))
//   					.withFilter(QueryBuilders.termsQuery("gender",  "M"))
//   					.withFilter(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("deptNo",  deptNumbers)).must(QueryBuilders.termsQuery("gender",  "M")))
//   					.withFilter(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("deptNo",  deptNumbers))) 
//   					.withFilter(FilterBuilders.termFilter("deptNo",  deptNumbers))
//   					.withFilter(dateRangeQueryBuilder)
//   					.withFilter(QueryBuilders.boolQuery().must(dateRangeQueryBuilder))
    				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
    				.withPageable(new PageRequest(0, 10))
    				.build();
			} else {
				searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
	    				.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
	    				.withFilter(dateRangeQueryBuilder)
	    				.withPageable(new PageRequest(0, 10))
	    				.build();	
			}
			Page<Emp> result = elasticsearchTemplate.queryForPage(searchQuery,Emp.class);
    		return result;
    }

}
