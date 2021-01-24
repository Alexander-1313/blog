package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.repository.ArticleRepository;
import ru.leverx.blog.service.ArticleService;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Article article) {
        repository.save(article);
    }

    @Override
    public void delete(Article article) {
        repository.delete(article);
    }

    @Override
    public List<Article> findAll() {
        return repository.findAll();
    }

    @Override
    public Article findById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public List<Article> findByStatus(Status status) {
        return repository.findArticleByStatus(status);
    }
}
