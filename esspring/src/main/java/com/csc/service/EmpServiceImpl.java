package com.csc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	@Transactional
	public Emp save1() {
		Dept dept = jpaDeptRepository.findOne(70);
		Emp emp = new Emp();
		emp.setId(10413);
		emp.setFirstName("Krishna");
		emp.setDept(dept);
		jpaEmpRepository.save(emp);
		return emp;
	}

	@Override
	public String indexAllEmps() {
		Iterable<Emp> empListIterable = jpaFindAll();
		empRepository.save(empListIterable);
		return "Success";
	}

	@Override
	public Page<Emp> findByNameOrDept(Map<String, String> requestMap, Pageable pageable) {
		String searchText = requestMap.get("freeText");
		String retriveFromDate = requestMap.get("retriveFromDate");
		String retriveToDate = requestMap.get("retriveToDate");
		
		long fromDateMills = 0L;
		long toDateMills = 0L;
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = f.parse(retriveFromDate);
			fromDateMills = d.getTime();
			d = f.parse(retriveToDate);
			toDateMills = d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("salaries.fromDate").gte(fromDateMills).lte(toDateMills))
				.must(QueryBuilders.rangeQuery("salaries.toDate").gte(fromDateMills).lte(toDateMills));
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchText, "firstName", "lastName", "dept.deptName")
																.analyzer("standard");
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder)
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
				.withFilter(queryBuilder)
				.withPageable(pageable).build();

		/*FilterBuilder filter1 = FilterBuilders.rangeFilter("dateFin").gt(df.format(getDateDebutRecherche()));
		FilterBuilder filter2 = FilterBuilders.rangeFilter("dateDebut").lt( df.format(getDateFinRecherche()));
		client=ClientProvider.getTransportClient();

		    SearchResponse response = client.prepareSearch(INDEX_NAME).setTypes(TYPE_NAME).
		            setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(),FilterBuilders.andFilter(filter1,filter2)))
		            .execute()
		            .actionGet();*/
		    
		Page<Emp> matchingEntities = elasticsearchTemplate.queryForPage(searchQuery,Emp.class);
		return matchingEntities;
	}
	
	/*@Override
	public Page<Emp> findByDeptNo(String deptNo, PageRequest pageRequest) {
		return empRepository.findByDeptNo(deptNo, pageRequest);
	}*/

}
