package com.csc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.csc.doc.Article;

public interface ArticleService {

	Article save(Article article);
	Article findById(String id);
	Iterable<Article> findAll();
	
	Page<Article> findByAuthorsName(String name, PageRequest pageRequest);
}
