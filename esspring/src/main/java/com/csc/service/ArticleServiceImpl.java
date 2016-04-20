package com.csc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.csc.doc.Article;
import com.csc.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	
	@Override
	public Article save(Article article) {
		articleRepository.save(article);
		return article;
	}

	@Override
	public Article findById(String id) {
		return articleRepository.findOne(id);
	}

	@Override
	public Iterable<Article> findAll() {
		return articleRepository.findAll();
	}

	@Override
	public Page<Article> findByAuthorsName(String name, PageRequest pageRequest) {
		return articleRepository.findByAuthorsName(name, pageRequest);
	}

}
