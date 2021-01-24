package ru.leverx.blog.service;

import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;

import java.util.List;

public interface ArticleService {

    void save(Article article);

    void delete(Article article);

    List<Article> findAll();

    Article findById(Integer id);

    List<Article> findByStatus(Status status);
}
