package ru.leverx.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/articles")
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @PostMapping
    public Article addPost(@PathVariable String userId,
                           @RequestParam("title") String title,
                           @RequestParam("text") String text,
                           @RequestParam("status") String status){

        User user = userService.getById(Integer.parseInt(userId));

        Article article = new Article(title, text, Status.valueOf(status), new Date(), new Date(), user);
        articleService.save(article);

        return article;
    }

    @GetMapping
    public List<Article> getAllPublicPosts(@PathVariable String userId,
                                           @RequestParam Map<String, String> allRequestParam){

//        List<Article> articles = articleService.findByStatus(Status.PUBLIC);

        return articleService.filterArticles(allRequestParam);
    }

    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable String userId,
                                  @PathVariable String id){
        articleService.deleteById(Integer.parseInt(id));
    }

    @PutMapping("/{id}")
    public void editArticleById(@PathVariable String userId,
                                @PathVariable String id,
                                @RequestParam("title") String title,
                                @RequestParam("text") String text,
                                @RequestParam("status") String status){

        articleService.updateById(Integer.parseInt(id), title, text, status);
    }

    @GetMapping("/my")
    public List<Article> getArticleByUser(@PathVariable String userId){
        User user = userService.getById(Integer.parseInt(userId));

        return articleService.findByUser(user);
    }

    /**
     * фильтрация
     */
}
