package ru.leverx.blog.service;

import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;

import java.util.List;
import java.util.Map;

public interface CommentService {

    void save(Comment comment);

    void deleteById(Integer id);

    Comment findById(Integer id);

    List<Comment> findAll();

    List<Comment> findCommentByArticle(Article article);

    List<Comment> filterComments(Map<String, String> allRequestParams, String articleId);

    void deleteCommentByIdAndArticleAndCommentUser(Integer id, Article article, User user);

    Comment findCommentByCommentUserAndArticleAndId(User commentUser, Article article, Integer id);
}
