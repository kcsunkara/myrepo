package com.csc.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
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

import com.csc.doc.EmpDetailsCustom;
import com.csc.repository.EmpDetailsCustomRepository;

@Service
public class EmpDetailsCustomServiceImpl implements EmpDetailsCustomService {

	private static final Logger logger = Logger.getLogger(EmpDetailsCustomServiceImpl.class);
	
	@Autowired
	private EmpDetailsCustomRepository empDetailsRepository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public EmpDetailsCustom save(EmpDetailsCustom empDetails) {
		return empDetailsRepository.save(empDetails);
	}

	@Override
	public Iterable<EmpDetailsCustom> findAll() {
		return empDetailsRepository.findAll();
	}

	@Override
	public EmpDetailsCustom findById(Long emp_no) {
		return empDetailsRepository.findOne(emp_no);
	}

	@Override
	public Page<EmpDetailsCustom> findByFirstname(String firstname, PageRequest pageRequest) {
		return empDetailsRepository.findByFirstname(firstname, pageRequest);
	}

	@Override
	public Page<EmpDetailsCustom> findByNameOrDept(Map<String, String> requestMap, Pageable pageable) {
		String searchText = requestMap.get("freeText");
		String fromdate = requestMap.get("retriveFromDate");
		String todate = requestMap.get("retriveToDate");
		
		MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(searchText, "firstname", "lastname", "deptname")
																.analyzer("standard");;
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQueryBuilder)
				.withSort(SortBuilders.fieldSort("empno").order(SortOrder.DESC))
				.withFilter(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("fromdate").gte(fromdate).lte(fromdate)))
//				.withFilter(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("todate").gte(fromdate).lte(todate)))
//				.withFilter(QueryBuilder.rangeFilter("DATE").gte(startDate).lt(endDate),
				.withPageable(pageable).build();

		/*
		QueryBuilder queryBuilder = multiMatchQuery(searchInput, "id","firstName","lastName","title","nickName","location")
            .type(MatchQueryBuilder.Type.PHRASE_PREFIX).analyzer("standard");
        
        builder.withFilter(
        	FilterBuilders.andFilter(
        		FilterBuilders.rangeFilter("DATE").gte(startDate).lt(endDate), 
        		FilterBuilders.boolFilter().mustNot(FilterBuilders.termFilter("STATUS", "ACTIVE"))));  
        
        DateHistogramBuilder dhb = AggregationBuilders.dateHistogram("t2").field("timestamp").interval(DateHistogramInterval.MINUTE);

		BoolQueryBuilder bqb = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("timestamp").gte("now-90d").to("now").format("epoch_millis"));

		FilterAggregationBuilder fab = AggregationBuilders.filter("t1").filter(bqb).subAggregation(dhb);

		SearchResponse sr = TEFESConnector.getInstance().getClient().prepareSearch("index_name")
		.setTypes("type_name").setQuery(QueryBuilders.matchQuery("field_to_search", "text_to_search"))
		.addAggregation(fab).execute().actionGet();
        */

		Page<EmpDetailsCustom> matchingEntities = elasticsearchTemplate.queryForPage(searchQuery,EmpDetailsCustom.class);
		return matchingEntities;
	}

}
