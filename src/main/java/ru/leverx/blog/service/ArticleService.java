package ru.leverx.blog.service;

import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;

import java.util.List;

public interface ArticleService {

    void deleteById(Integer userId, Integer articleId);

    void save(Article article);

    void updateById(Integer articleId, Integer userId, String title, String text, String status);

    List<Article> findByUser(User user);

    Article findById(Integer id);

    Article findByTitle(String title);

    List<Article> filter(Integer skip, Integer limit, String q, Integer authorId, String fieldName, String order, Integer authorIdFromRequest);

    List<Article> findByTagId(Integer id);
}
