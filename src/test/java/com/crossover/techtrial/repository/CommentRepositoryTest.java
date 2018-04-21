package com.crossover.techtrial.repository;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.model.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void TestComment() {
        Article article = new Article();
        article.setId(new Long(1));
        article.setDate(LocalDateTime.now());
        article.setContent("content");
        article.setEmail("email@eail.com");
        article.setPublished(true);
        article.setTitle("hello");
        articleRepository.save(article);

        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setDate(LocalDateTime.now());
        comment.setEmail("test@test.com");
        comment.setMessage("message");
        comment.setId(new Long(1));
        commentRepository.save(comment);

        List<Comment> all = commentRepository.findAll();
        Assert.assertEquals(all.size(), 1);

        comment = new Comment();
        comment.setArticle(article);
        comment.setDate(LocalDateTime.now());
        comment.setEmail("test@test.com");
        comment.setMessage("message");
        comment.setId(new Long(2));
        commentRepository.save(comment);

        comment = new Comment();
        comment.setArticle(article);
        comment.setDate(LocalDateTime.now());
        comment.setEmail("test@test.com");
        comment.setMessage("message");
        comment.setId(new Long(3));
        commentRepository.save(comment);

        List<Comment> byArticleIdOrderByDate = commentRepository.findByArticleIdOrderByDate(article.getId());

        Assert.assertTrue(byArticleIdOrderByDate.get(0).getId().equals(new Long(1)));
        Assert.assertTrue(byArticleIdOrderByDate.get(1).getId().equals(new Long(2)));
        Assert.assertTrue(byArticleIdOrderByDate.get(2).getId().equals(new Long(3)));

    }

}
