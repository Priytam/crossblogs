package com.crossover.techtrial.controller;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.model.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    @Sql(scripts="classpath:cleanup.sql",executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCreateComment() throws Exception {

        ResponseEntity<Article> articleResponseEntity = TestUtil.addArticle(template, "user1@gmail.com", "hello");
        Long id = articleResponseEntity.getBody().getId();

        HttpEntity<Object> commentEntity = TestUtil.getHttpEntity("{\"email\": \"comm@gmail.com\", \"article_id\": \""+id+"\",  \"message\": \"This is comment\"}");
        ResponseEntity<Comment> commentResponseEntity = template.postForEntity("/articles/" + id + "/comments", commentEntity, Comment.class);

        Comment body = commentResponseEntity.getBody();
        Assert.assertNotNull(body.getId());
        Assert.assertEquals(body.getMessage(), "This is comment");
        Assert.assertEquals(body.getEmail(), "comm@gmail.com");
        Assert.assertNull(body.getArticle());
        Assert.assertNull(body.getDate());

    }

    @Test
    @Sql(scripts="classpath:cleanup.sql",executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetComments() throws Exception {

        ResponseEntity<Article> articleResponseEntity = TestUtil.addArticle(template, "user1@gmail.com", "hello");
        Long id = articleResponseEntity.getBody().getId();

        HttpEntity<Object> commentEntity = TestUtil.getHttpEntity("{\"email\": \"comm@gmail.com\", \"article_id\": \""+id+"\",  \"message\": \"This is comment\"}");
        template.postForEntity("/articles/" + id + "/comments", commentEntity, Comment.class);

        ResponseEntity<Comment[]> forEntity = template.getForEntity("/articles/" + id + "/comments", Comment[].class);
        Assert.assertEquals(forEntity.getBody().length, 1);
    }
}
