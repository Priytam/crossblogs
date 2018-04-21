package com.crossover.techtrial.controller;

import com.crossover.techtrial.model.Article;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleControllerTest {

	@Autowired
	private TestRestTemplate template;

	@Test
	public void testArticleShouldBeCreated() throws Exception {
		ResponseEntity<Article> resultAsset = TestUtil.addArticle(template, "user1@gmail.com", "hello");
		Assert.assertNotNull(resultAsset.getBody().getId());
		Assert.assertEquals(resultAsset.getBody().getEmail(), "user1@gmail.com");
		Assert.assertEquals(resultAsset.getBody().getTitle(), "hello");
		Assert.assertNull(resultAsset.getBody().getPublished());
		Assert.assertNull(resultAsset.getBody().getContent());
	}

	@Test
	public void testContentAndPublished() throws Exception {
		HttpEntity<Object> articleEntity = TestUtil.getHttpEntity("{\"email\": \"user1@gmail.com\", \"title\": \"title\", \"published\": \"true\", \"content\": \"content\" }");
		ResponseEntity<Article> resultAsset = template.postForEntity("/articles", articleEntity, Article.class);
		Article body = resultAsset.getBody();
		Assert.assertEquals(body.getTitle(), "title");
		Assert.assertTrue(body.getPublished());
		Assert.assertEquals(body.getContent(), "content");
	}

	@Test
	public void testGetArticleByID() throws Exception {
		ResponseEntity<Article> resultAsset = TestUtil.addArticle(template, "user1@gmail.com", "hello");
		Long id = resultAsset.getBody().getId();
		ResponseEntity<Article> getResult = TestUtil.getArticle(template, id);
		Assert.assertEquals(id, getResult.getBody().getId());
	}

	@Test
	public void testUpdateArticleByID() throws Exception {
		ResponseEntity<Article> resultAsset = TestUtil.addArticle(template, "user1@gmail.com", "hello");

		Article article = resultAsset.getBody();
		Long id = article.getId();
		String content = "I am test Content";
		article.setContent(content);

		template.put("/articles/" + id, article);
		ResponseEntity<Article> getResult = TestUtil.getArticle(template, id);

		Assert.assertEquals(content, getResult.getBody().getContent());

		LocalDateTime now = LocalDateTime.now();
		article.setDate(now);
		template.put("/articles/" + id, article);
		getResult = TestUtil.getArticle(template, id);

		Assert.assertEquals(now.getYear(), getResult.getBody().getDate().getYear());

	}

	@Test
	public void testDeleteArticleByID() throws Exception {
		ResponseEntity<Article> resultAsset = TestUtil.addArticle(template, "user1@gmail.com", "hello");

		Long id =  resultAsset.getBody().getId();
		template.delete("/articles/" + id);
		ResponseEntity<Article> getResult = TestUtil.getArticle(template, id);

		Assert.assertNull(getResult.getBody());
	}

	@Test
	@Sql(scripts="classpath:cleanup.sql",executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testSearchArticles() throws Exception {

		TestUtil.addArticle(template, "user1@gmail.com", "hello");
		TestUtil.addArticle(template, "user2@gmail.com", "I am test");
		TestUtil.addArticle(template, "user3@gmail.com", "Hello is test");
		TestUtil.addArticle(template, "user4@gmail.com", "hello there");
		TestUtil.addArticle(template, "user5@gmail.com", "test is bliss help");
		TestUtil.addArticle(template, "user6@gmail.com", "hello test");

		ResponseEntity<Article[]> forEntity = template.getForEntity("/articles/search?text=hello", Article[].class);
		Assert.assertEquals(forEntity.getBody().length, 4);

		forEntity = template.getForEntity("/articles/search?text=test", Article[].class);
		Assert.assertEquals(forEntity.getBody().length, 4);
	}
}