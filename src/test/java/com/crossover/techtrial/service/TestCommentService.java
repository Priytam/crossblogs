package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.model.Comment;
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
public class TestCommentService {

    @Autowired
    ArticleServiceImpl articleService;

    @Autowired
    CommentServiceImpl commentService;

    @Test
    @Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCommentCreateUpdateDelete() {

        Article article = new Article();
        article.setId(new Long(1));
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("hello");
        articleService.save(article);

        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setEmail("test@test.com");
        comment.setMessage("message");
        comment.setId(new Long(1));

        commentService.save(comment);

        List<Comment> all = commentService.findAll(article.getId());

        Assert.assertEquals(all.size(), 1);
    }

}
