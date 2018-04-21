package com.crossover.techtrial;

import com.crossover.techtrial.model.Article;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;

public class TestUtil {

    public static void deleteIndexStore() {
        File index = new File("com.crossover.techtrial.model.Article");

        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
    }

    public static ResponseEntity<Article> getArticle(TestRestTemplate template, Long id) {
        return template.getForEntity("/articles/" + id, Article.class);
    }

    public static ResponseEntity<Article> addArticle(TestRestTemplate template, String sMailID, String sTitle) {
        HttpEntity<Object> articleEntity = getHttpEntity("{\"email\": \""+sMailID+"\", \"title\": \""+sTitle+"\" }");

        return template.postForEntity("/articles", articleEntity, Article.class);
    }

    public static ResponseEntity<Article> addArticle(TestRestTemplate template) {
        return addArticle(template, "user1@gmail.com", "hello");
    }

    public static HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
}
