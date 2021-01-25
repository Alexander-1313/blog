package ru.leverx.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.CommentService;
import ru.leverx.blog.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{userId}/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, ArticleService articleService) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
    }

    @PostMapping
    public void addComment(@PathVariable String userId,
                           @PathVariable String articleId,
                           @RequestParam("text") String text){

        User user = userService.getById(Integer.parseInt(userId));
        Article article = articleService.findById(Integer.parseInt(articleId));
        Comment comment = new Comment(text, new Date(), user, article);

        commentService.save(comment);
    }

    @GetMapping
    public List<Comment> findCommentByArticle(@PathVariable String userId,
                                              @PathVariable String articleId,
                                              @RequestParam Map<String, String> allRequestParam){

        return commentService.filterComments(allRequestParam, articleId);
    }

    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable String userId,
                                  @PathVariable String articleId,
                                  @PathVariable String commentId){

        return commentService.findById(Integer.parseInt(commentId));
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable String userId,
                                  @PathVariable String articleId,
                                  @PathVariable String commentId){

        commentService.deleteById(Integer.parseInt(commentId));
    }

    /**
     * настроить отображение json
     */
}
