package com.csc.service;

import java.util.List;

import com.csc.doc.Article;

public interface ArticleService {

	Article save(Article article);
	Article findById(String id);
	Iterable<Article> findAll();
	
//	Page<Article> findByAuthorsName(String name, PageRequest pageRequest);
	List<Article> findByAuthorsName(String name);
}
