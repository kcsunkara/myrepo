package com.csc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.csc.doc.Article;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
	
	Page<Article> findByAuthorsName(String name, Pageable pageable);

}
