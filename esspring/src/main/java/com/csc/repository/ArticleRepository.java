package com.csc.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.csc.doc.Article;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
	
//	Page<Article> findByAuthorsName(String name, Pageable pageable);
	List<Article> findByAuthorsName(String name);

}
