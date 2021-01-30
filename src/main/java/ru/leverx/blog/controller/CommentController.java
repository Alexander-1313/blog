package ru.leverx.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.CommentService;
import ru.leverx.blog.service.UserService;
import ru.leverx.blog.util.RequestUtil;
import ru.leverx.blog.util.View;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;
    private final RequestUtil requestUtil;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, ArticleService articleService, RequestUtil requestUtil) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
        this.requestUtil = requestUtil;
    }

    @PostMapping
    @JsonView(View.UI.class)
    public void addComment(@PathVariable String userId,
                           @PathVariable String articleId,
                          @RequestBody Comment comment,
                           HttpServletRequest request){
        if(requestUtil.isConfirmUser(request, userId) && comment != null) {
            User user = userService.findById(requestUtil.strToInt(userId));
            Article article = articleService.findById(requestUtil.strToInt(articleId));
            comment.setCreatedAt(new Date());
            comment.setArticle(article);
            comment.setCommentUser(user);
            commentService.save(comment);
        }
    }

    @GetMapping
    @JsonView(View.UI.class)
    public List<Comment> findCommentByArticle(@PathVariable String userId,
                                              @PathVariable String articleId,
                                              @RequestParam Map<String, String> allRequestParam){

        Integer skip = requestUtil.strToInt(allRequestParam.get("skip"));
        Integer limit = requestUtil.strToInt(allRequestParam.get("limit"));
        Integer authorId = requestUtil.strToInt(allRequestParam.get("authorId"));
        String q = allRequestParam.get("q");
        String fieldName = allRequestParam.get("fieldName");
        String order = allRequestParam.get("order");

        return commentService.filter(skip, limit, q, authorId, fieldName, order, requestUtil.strToInt(userId));
    }

    @GetMapping("/{commentId}")
    @JsonView(View.UI.class)
    public Comment getCommentById(@PathVariable String userId,
                                  @PathVariable String articleId,
                                  @PathVariable String commentId){

        User user = userService.findById(requestUtil.strToInt(userId));
        Article article = articleService.findById(requestUtil.strToInt(articleId));

        return commentService.findByIdAndUserAndArticle(requestUtil.strToInt(commentId), user, article);
    }

    @DeleteMapping("/{commentId}")
    @JsonView(View.UI.class)
    public void deleteCommentById(@PathVariable String userId,
                                  @PathVariable String articleId,
                                  @PathVariable String commentId,
                                  HttpServletRequest request){

        if(requestUtil.isConfirmUser(request, userId)) {
            Article article = articleService.findById(requestUtil.strToInt(articleId));
            User user = userService.findById(requestUtil.strToInt(userId));
            commentService.deleteCommentByIdAndArticleAndCommentUser(requestUtil.strToInt(commentId), article, user);
        }
    }

}
