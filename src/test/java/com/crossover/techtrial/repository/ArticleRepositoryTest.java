package com.crossover.techtrial.repository;

import com.crossover.techtrial.model.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void TestArticle() {
        Article article = new Article();
        article.setId(new Long(1));
        article.setDate(LocalDateTime.now());
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("hello");
        articleRepository.save(article);

        article = new Article();
        article.setId(new Long(1));
        article.setDate(LocalDateTime.now());
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("hell");
        articleRepository.save(article);

        article = new Article();
        article.setId(new Long(1));
        article.setDate(LocalDateTime.now());
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("test");
        articleRepository.save(article);

        article = new Article();
        article.setId(new Long(1));
        article.setDate(LocalDateTime.now());
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("test hello");
        articleRepository.save(article);

        List<Article> hell = articleRepository.findTop10ByTitleContainingIgnoreCase("hell");

        Assert.assertEquals(hell.size(), 3);
    }
}
