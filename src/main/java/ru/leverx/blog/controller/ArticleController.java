package ru.leverx.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.Tag;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.TagService;
import ru.leverx.blog.service.UserService;
import ru.leverx.blog.util.RequestUtil;
import ru.leverx.blog.util.View;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/users/{userId}/articles")
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;
    private RequestUtil requestUtil;
    private TagService tagService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService, RequestUtil requestUtil, TagService tagService) {
        this.articleService = articleService;
        this.userService = userService;
        this.requestUtil = requestUtil;
        this.tagService = tagService;
    }

    @JsonView(View.UI.class)
    @PostMapping
    public Article addPost(@PathVariable String userId,
                           @RequestParam("title") String title,
                           @RequestParam("text") String text,
                           @RequestParam("status") String status,
                           @RequestParam("tags") Set<String> tags,
                           HttpServletRequest request){
        Article article = null;
        List<Tag> tagSet = new ArrayList<>();
        Tag dbTag;

        for(String tagName: tags){
            dbTag = tagService.findByName(tagName);

            if(dbTag == null) {
                Tag tag = new Tag(tagName);
                tagSet.add(tag);
            }else{
                tagSet.add(dbTag);
            }
        }

        if(requestUtil.isConfirmUser(request, userId)) {
            User user = userService.findById(Integer.parseInt(userId));
            article = new Article(title, text, Status.valueOf(status), new Date(), new Date(), user, tagSet);
            articleService.save(article);
        }
        return article;
    }

    @JsonView(View.UI.class)
    @GetMapping
    public List<Article> getAllPublicPosts(@PathVariable String userId,
                                           @RequestParam Map<String, String> allRequestParam) {

        Integer skip = Integer.parseInt(allRequestParam.get("skip"));
        Integer limit = Integer.parseInt(allRequestParam.get("limit"));
        Integer authorId = Integer.parseInt(allRequestParam.get("authorId"));
        String q = allRequestParam.get("q");
        String fieldName = allRequestParam.get("fieldName");
        String order = allRequestParam.get("order");


            return articleService.filter(skip, limit, q, authorId, fieldName, order, Integer.parseInt(userId));
    }

    @JsonView(View.UI.class)
    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable String userId,
                                  @PathVariable String id,
                                  HttpServletRequest request) {
        if (requestUtil.isConfirmUser(request, userId)) {
            articleService.deleteById(Integer.parseInt(userId), Integer.parseInt(id));
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
            User user = userService.findById(Integer.parseInt(userId));
            return articleService.findByUser(user);
        }else{
            return null;
        }
    }

}