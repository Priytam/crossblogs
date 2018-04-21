package com.crossover.techtrial.service;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.repository.ArticleRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public Article save(Article article) {
		return articleRepository.save(article);
	}

	public Article findById(Long id) {
		return articleRepository.findById(id).orElse(null);
	}

	public void delete(Long id) {
		articleRepository.deleteById(id);
	}

	public List<Article> search(String search) {
		EntityManager em = entityManagerFactory.createEntityManager();

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		em.getTransaction().begin();

		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
		Query luceneQuery = qb
				.keyword()
				.onFields("title")
				.matching(search)
				.createQuery();

		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Article.class);

		List<Article> result = jpaQuery.getResultList();

		em.getTransaction().commit();
		em.close();
		return result;
	}

}