package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leverx.blog.comparator.ArticleComparator;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.repository.ArticleRepository;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;
    private final UserService service;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;
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

    @Override
    public List<Article> findByUser(User user) {
        return repository.findArticleByUser(user);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteArticlesById(id);
    }

    @Override
    public void updateById(Integer id, Integer userId, String title, String text, String status) {
        Article article = findArticleByIdAndUser(id, service.getById(userId));
        if(article != null) {
            if (title != null) {
                article.setTitle(title);
            }
            if (text != null) {
                article.setText(text);
            }
            if (status != null) {
                article.setStatus(Status.valueOf(status));
            }

            article.setUpdatedAt(new Date());

            repository.save(article);
        }
    }

    private List<Article> findArticleByTitleAndUser(String title, User user) {
        List<Article> articles =  repository.findArticleByTitleAndUser(title, user);
        List<Article> resArticles =  articles.stream().filter(a -> a.getStatus() == Status.PUBLIC).collect(Collectors.toList());
        return  resArticles;
    }

    private List<Article> sort(List<Article> articles, String sort, String order) {
        if(sort == null){
            return articles;
        }
        if(order.equalsIgnoreCase("asc") || order == null) {
            Collections.sort(articles, ArticleComparator.valueOf(sort.toUpperCase(Locale.ROOT)));
        }else{
            Collections.sort(articles, ArticleComparator.valueOf(sort.toUpperCase(Locale.ROOT)).reversed());
        }
        return articles;
    }

    private List<Article> filterArticlesWithSkipAndLimit(List<Article> articles, Integer skip, Integer limit) {
        List<Article> filteredArticles = new ArrayList<Article>();
        Integer lim = 0;

        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = articles.size();
        }

        for(int i = skip; i < articles.size(); i++){
            if(lim < limit){
                Article article = articles.get(i);
                filteredArticles.add(article);
                lim++;
            }
        }

        return filteredArticles;
    }

    @Override
    public List<Article> filterArticles(Map<String, String> allRequestParams) {
        String title = allRequestParams.get("title");
        String author = allRequestParams.get("author");

        List<Article> articles = findArticleByTitleAndUser(allRequestParams.get(title), service.getById(Integer.parseInt(allRequestParams.get(author))));
        sort(articles, allRequestParams.get("sort"), allRequestParams.get("order"));
        articles = filterArticlesWithSkipAndLimit(articles, Integer.parseInt(allRequestParams.get("skip")), Integer.parseInt(allRequestParams.get("limit")));
        System.out.println(allRequestParams.size());

        return articles;
    }

    @Override
    public void deleteArticlesByIdAndUser(Integer id, User user) {
        repository.deleteArticlesByIdAndUser(id, user);
    }

    @Override
    public Article findArticleByIdAndUser(Integer id, User user) {
        return repository.findArticleByIdAndUser(id, user);
    }


}
