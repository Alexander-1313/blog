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
import ru.leverx.blog.util.StringConstant;
import ru.leverx.blog.util.View;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/articles")
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
    public Article addPost(@RequestBody Article article,
                           HttpServletRequest request){
        List<Tag> tagSet = new ArrayList<>();
        Tag dbTag;

        for(Tag tag: article.getTags()){
            dbTag = tagService.findByName(tag.getName());

            if(dbTag == null) {
                tagSet.add(tag);
            }else{
                tagSet.add(dbTag);
            }
        }

        if(requestUtil.hasAccess(request)) {
            User user = userService.findById(requestUtil.getUserIdByRequest(request));
            article.setUpdatedAt(new Date());
            article.setCreatedAt(new Date());
            article.setTags(tagSet);
            article.setUser(user);
            articleService.save(article);
        }
        return article;
    }

    @JsonView(View.UI.class)
    @GetMapping
    public List<Article> getAllPublicPosts(@RequestParam Map<String, String> allRequestParam,
                                           HttpServletRequest request) { //TODO

        Integer skip = requestUtil.strToInt(allRequestParam.get(StringConstant.SKIP));
        Integer limit = requestUtil.strToInt(allRequestParam.get(StringConstant.LIMIT));
        Integer authorId = requestUtil.strToInt(allRequestParam.get(StringConstant.AUTHOR_ID));
        String q = allRequestParam.get(StringConstant.Q);
        String fieldName = allRequestParam.get(StringConstant.FIELD_NAME);
        String order = allRequestParam.get(StringConstant.ORDER);

        return articleService.filter(skip, limit, q, authorId, fieldName, order, requestUtil.getUserIdByRequest(request));
    }

    @JsonView(View.UI.class)
    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable String id,
                                  HttpServletRequest request) {
        if (requestUtil.hasAccess(request) && requestUtil.hasArticle(request, requestUtil.strToInt(id))) {
            articleService.deleteById(requestUtil.getUserIdByRequest(request), requestUtil.strToInt(id));
        }
    }

    @JsonView(View.UI.class)
    @PutMapping("/{id}")
    public void editArticleById(@PathVariable String id,
                                @RequestBody Article article,
                                HttpServletRequest request) {
        if (requestUtil.hasAccess(request) && requestUtil.hasArticle(request, requestUtil.strToInt(id))) {
            articleService.updateById(requestUtil.strToInt(id), requestUtil.getUserIdByRequest(request), article);
        }
    }

    @JsonView(View.UI.class)
    @GetMapping("/my")
    public List<Article> getArticleByUser(HttpServletRequest request){

        if (requestUtil.hasAccess(request)) {
            User user = userService.findById(requestUtil.getUserIdByRequest(request));
            return articleService.findByUser(user);
        }else{
            return null;
        }
    }

}