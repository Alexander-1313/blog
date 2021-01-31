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
import ru.leverx.blog.util.StringConstant;
import ru.leverx.blog.util.View;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/articles/{articleId}/comments")
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
    public void addComment(@PathVariable String articleId,
                          @RequestBody Comment comment,
                           HttpServletRequest request){
        Integer intArtId = requestUtil.strToInt(articleId);

        if(requestUtil.hasAccess(request) && comment != null && requestUtil.hasArticle(request, intArtId)) {
            User user = userService.findById(requestUtil.getUserIdByRequest(request));
            Article article = articleService.findById(intArtId);
            comment.setCreatedAt(new Date());
            comment.setArticle(article);
            comment.setCommentUser(user);
            commentService.save(comment);
        }
    }

    @GetMapping
    @JsonView(View.UI.class)
    public List<Comment> findCommentByArticle(@PathVariable String articleId,
                                              @RequestParam Map<String, String> allRequestParam,
                                              HttpServletRequest request){ //TODO

        Integer skip = requestUtil.strToInt(allRequestParam.get(StringConstant.SKIP));
        Integer limit = requestUtil.strToInt(allRequestParam.get(StringConstant.LIMIT));
        Integer authorId = requestUtil.strToInt(allRequestParam.get(StringConstant.AUTHOR_ID));
        String q = allRequestParam.get(StringConstant.Q);
        String fieldName = allRequestParam.get(StringConstant.FIELD_NAME);
        String order = allRequestParam.get(StringConstant.ORDER);

        return commentService.filter(skip, limit, q, authorId, fieldName, order, requestUtil.getUserIdByRequest(request));
    }

    @GetMapping("/{commentId}")
    @JsonView(View.UI.class)
    public Comment getCommentById(@PathVariable String articleId,
                                  @PathVariable String commentId,
                                  HttpServletRequest request){

        User user = userService.findById(requestUtil.getUserIdByRequest(request));
        Article article = articleService.findById(requestUtil.strToInt(articleId));

        return commentService.findByIdAndUserAndArticle(requestUtil.strToInt(commentId), user, article);
    }

    @DeleteMapping("/{commentId}")
    @JsonView(View.UI.class)
    public void deleteCommentById(@PathVariable String articleId,
                                  @PathVariable String commentId,
                                  HttpServletRequest request){
        Integer intArtId = requestUtil.strToInt(articleId);

        if(requestUtil.hasAccess(request) && requestUtil.hasArticle(request, intArtId)) {
            Article article = articleService.findById(intArtId);
            User user = userService.findById(requestUtil.getUserIdByRequest(request));
            commentService.deleteCommentByIdAndArticleAndCommentUser(requestUtil.strToInt(commentId), article, user);
        }
    }

}
