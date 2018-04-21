package com.crossover.techtrial.repository;

import com.crossover.techtrial.model.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @Sql(scripts="classpath:cleanup.sql",executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void TestArticle() {
        Article article = new Article();
        article.setId(new Long(1));
        article.setDate(LocalDateTime.now());
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("hello");
        articleRepository.save(article);

        Article article1 = new Article();
        article1.setId(new Long(2));
        article1.setDate(LocalDateTime.now());
        article1.setContent("content");
        article1.setEmail("email@eail.com");
        article1.setPublished(true);
        article1.setTitle("Hello");
        articleRepository.save(article1);

        Article article2 = new Article();
        article2.setId(new Long(3));
        article2.setDate(LocalDateTime.now());
        article2.setContent("content");
        article2.setEmail("email@eail.com");
        article2.setPublished(true);
        article2.setTitle("test");
        articleRepository.save(article2);

        Article article3 = new Article();
        article3.setId(new Long(4));
        article3.setDate(LocalDateTime.now());
        article3.setContent("content");
        article3.setEmail("email@eail.com");
        article3.setPublished(true);
        article3.setTitle("test hello");
        articleRepository.save(article3);

        List<Article> hell = articleRepository.findTop10ByTitleContainingIgnoreCase("hello");

        Assert.assertEquals(3, hell.size());
    }
}
