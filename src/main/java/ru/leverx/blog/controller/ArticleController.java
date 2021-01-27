package ru.leverx.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.UserService;
import ru.leverx.blog.util.RequestUtil;
import ru.leverx.blog.util.View;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/articles")
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;
    private RequestUtil requestUtil;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService, RequestUtil requestUtil) {
        this.articleService = articleService;
        this.userService = userService;
        this.requestUtil = requestUtil;
    }

    @JsonView(View.UI.class)
    @PostMapping
    public Article addPost(@PathVariable String userId,
                           @RequestParam("title") String title,
                           @RequestParam("text") String text,
                           @RequestParam("status") String status,
                           HttpServletRequest request){
        Article article = null;

        if(requestUtil.isConfirmUser(request, userId)) {
            User user = userService.getById(Integer.parseInt(userId));
            article = new Article(title, text, Status.valueOf(status), new Date(), new Date(), user);
            articleService.save(article);
        }
        return article;
    }

    @JsonView(View.UI.class)
    @GetMapping
    public List<Article> getAllPublicPosts(@PathVariable String userId,
                                           @RequestParam Map<String, String> allRequestParam) { //TODO

//        List<Article> articles = articleService.findByStatus(Status.PUBLIC);
            return articleService.filterArticles(allRequestParam);
    }

    @JsonView(View.UI.class)
    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable String userId,
                                  @PathVariable String id,
                                  HttpServletRequest request) {
        if (requestUtil.isConfirmUser(request, userId)) {
            articleService.deleteArticlesByIdAndUser(Integer.parseInt(id), userService.getById(Integer.parseInt(userId)));
        }
    }

    @JsonView(View.UI.class)
    @PutMapping("/{id}")
    public void editArticleById(@PathVariable String userId,
                                @PathVariable String id,
                                @RequestParam("title") String title,
                                @RequestParam("text") String text,
                                @RequestParam("status") String status,
                                HttpServletRequest request) {

        if (requestUtil.isConfirmUser(request, userId)) {
            articleService.updateById(Integer.parseInt(id), Integer.parseInt(userId), title, text, status);
        }
    }

    @JsonView(View.UI.class)
    @GetMapping("/my")
    public List<Article> getArticleByUser(@PathVariable String userId,
                                          HttpServletRequest request){

        if (requestUtil.isConfirmUser(request, userId)) {
            User user = userService.getById(Integer.parseInt(userId));
            return articleService.findByUser(user);
        }else{
            return null;
        }
    }

}
