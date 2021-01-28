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
                           @RequestParam("text") String text,
                           HttpServletRequest request){
        if(requestUtil.isConfirmUser(request, userId)) {
            User user = userService.findById(Integer.parseInt(userId));
            Article article = articleService.findById(Integer.parseInt(articleId));
            Comment comment = new Comment(text, new Date(), user, article);
            commentService.save(comment);
        }
    }

    @GetMapping
    @JsonView(View.UI.class)
    public List<Comment> findCommentByArticle(@PathVariable String userId,
                                              @PathVariable String articleId,
                                              @RequestParam Map<String, String> allRequestParam){ //TODO

//        return commentService.filterComments(allRequestParam, articleId);
        return null;
    }

    @GetMapping("/{commentId}")
    @JsonView(View.UI.class)
    public Comment getCommentById(@PathVariable String userId,
                                  @PathVariable String articleId,
                                  @PathVariable String commentId){ // валидация статьи и коммента

        User user = userService.findById(Integer.parseInt(userId));
        Article article = articleService.findById(Integer.parseInt(articleId));

        return commentService.findById(Integer.parseInt(commentId));
    }

    @DeleteMapping("/{commentId}")
    @JsonView(View.UI.class)
    public void deleteCommentById(@PathVariable String userId,
                                  @PathVariable String articleId,
                                  @PathVariable String commentId,
                                  HttpServletRequest request){

        if(requestUtil.isConfirmUser(request, userId)) {
            Article article = articleService.findById(Integer.parseInt(articleId));
            User user = userService.findById(Integer.parseInt(userId));
            commentService.deleteCommentByIdAndArticleAndCommentUser(Integer.parseInt(commentId), article, user);
        }
    }

}
