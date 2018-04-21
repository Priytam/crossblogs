package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestArticleService {

    @Autowired
    ArticleServiceImpl articleService;

    @Test
    @Sql(scripts="classpath:cleanup.sql",executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testArticleCreateUpdateDelete() {

        Article article = new Article();
        article.setId(new Long(1));
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("hello");
        articleService.save(article);
        Article byId = articleService.findById(new Long(1));

        Assert.assertEquals(byId.getContent(), article.getContent());

        List<Article> hello = articleService.search("hello");
        Assert.assertEquals(hello.size(), 1);
        Assert.assertEquals(hello.get(0).getTitle(), "hello");

        Long id = article.getId();
        articleService.delete(id);
        byId = articleService.findById(id);

        Assert.assertNull(byId);

    }
}
