package ru.leverx.blog.service;

import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;

public interface CommentService {

    void save(Comment comment);

    Comment findById(Integer id);

    void deleteCommentByIdAndArticleAndCommentUser(Integer id, Article article, User user);
}
