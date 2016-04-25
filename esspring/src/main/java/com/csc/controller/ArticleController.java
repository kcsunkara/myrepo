package com.csc.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.csc.doc.Article;
import com.csc.doc.Author;
import com.csc.service.ArticleService;

@RestController 
@RequestMapping("/")
public class ArticleController {
	
	private static final Logger logger = Logger.getLogger(ArticleController.class);
	
	@Autowired
	ArticleService articleService;
	
	@RequestMapping("/")
	public String defaultView() {
		return "forward:/index.html";
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String sayHello() {
		return "Hello! Welcome to the App...";
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public @ResponseBody Article findById(@RequestParam String id) {
		return articleService.findById(id);
	}
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public @ResponseBody Iterable<Article> findById() {
		return articleService.findAll();
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Article saveArticle(@RequestBody Article article) {
		return articleService.save(article);
	}
	
	@RequestMapping(value = "/findByAuthorsName", method = RequestMethod.GET)
	public @ResponseBody List<Article> findByAuthorsName(@RequestParam String authorName) {
		logger.info("ArticleController.findByAuthorsName() --> authorName = " + authorName);
		List<Article> pages = null;
		pages = articleService.findByAuthorsName(authorName);
		return pages;
	}
	
	@RequestMapping(value = "/save1", method = RequestMethod.GET)
	public @ResponseBody Article saveArticle() {

		Author author = new Author();
		author.setId("718");
		author.setName("KCSunkara");
		
		Article article = new Article();
		article.setAuthors(Arrays.asList(author));
		article.setTitle("My First Article in ES");
		
		return articleService.save(article);
	}

}
