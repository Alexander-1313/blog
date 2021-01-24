package ru.leverx.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.service.ArticleService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/")
    public Article addPost(@RequestParam("title") String title,
                           @RequestParam("text") String text,
                           @RequestParam("status") String status){

        Article article = new Article(title, text, Status.valueOf(status), new Date(), new Date());
        articleService.save(article);

        return article;
    }

    @GetMapping("/")
    public List<Article> getAllPublicPosts(){

        return articleService.findByStatus(Status.PUBLIC);
    }
}
