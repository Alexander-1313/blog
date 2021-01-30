package ru.leverx.blog.service;

import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;

import java.util.List;

public interface CommentService {

    void save(Comment comment);

    Comment findById(Integer id);

    Comment findByIdAndUserAndArticle(Integer id, User user, Article article);

    void deleteCommentByIdAndArticleAndCommentUser(Integer id, Article article, User user);

    List<Comment> filter(Integer skip, Integer limit, String q, Integer authId, String fieldName, String order, Integer authorIdFromRequest);
}
