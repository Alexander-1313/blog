package ru.leverx.blog.service;

import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    void save(Article article);

    void delete(Article article);

    List<Article> findAll();

    Article findById(Integer id);

    List<Article> findByStatus(Status status);

    List<Article> findByUser(User user);

    void deleteById(Integer id);

    void updateById(Integer id, Integer userId, String title, String text, String status);

    List<Article> filterArticles(Map<String, String> allRequestParams);

    void deleteArticlesByIdAndUser(Integer id, User user);

    Article findArticleByIdAndUser(Integer id, User user);

}
