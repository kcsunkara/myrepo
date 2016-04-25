package com.csc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Article> findByAuthorsName(String name) {
		return articleRepository.findByAuthorsName(name);
	}

}
