package ru.leverx.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.service.ArticleService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/articles")
    public Article addPost(@RequestParam("title") String title,
                           @RequestParam("text") String text,
                           @RequestParam("status") String status){

        Article article = new Article(title, text, Status.valueOf(status), new Date(), new Date());
        articleService.save(article);

        return article;
    }

    @GetMapping("/articles")
    public List<Article> getAllPublicPosts(){

        return articleService.findByStatus(Status.PUBLIC);
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticleById(@PathVariable String id){
        articleService.deleteById(Integer.parseInt(id));
    }

    /**
     *добавить редактирование статьи
     * получить посты авторизованного пользователя
     * фильтрация
     */
}
