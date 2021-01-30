package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.stereotype.Service;
import ru.leverx.blog.comparator.ArticleComparator;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.Tag;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.repository.ArticleRepository;
import ru.leverx.blog.repository.TagRepository;
import ru.leverx.blog.repository.UserRepository;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.CommentService;
import ru.leverx.blog.service.TagService;
import ru.leverx.blog.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final TagService tagService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository, UserService service, TagService tagService) {
        this.articleRepository = repository;
        this.userService = service;
        this.tagService = tagService;
    }

    @Override
    public void deleteById(Integer userId, Integer articleId) {
        User user = userService.findById(userId);

        Article article = articleRepository.findByIdAndUser(articleId, user);

        if (article != null) {
            articleRepository.deleteArticle(articleId);
        }
    }

    @Override
    public void save(Article article) {
        if (article != null) {
            articleRepository.save(article);
        }
    }

    @Override
    public void updateById(Integer articleId, Integer userId, Article article) {
        User user = userService.findById(userId);
        Article articleDb = articleRepository.findByIdAndUser(articleId, user);
        if (articleDb != null) {
            if (article.getTitle() != null) {
                articleDb.setTitle(article.getTitle());
            }
            if (article.getText() != null) {
                articleDb.setText(article.getText());
            }
            if (article.getStatus() != null) {
                articleDb.setStatus(article.getStatus());
            }

            articleDb.setUpdatedAt(new Date());

            articleRepository.save(articleDb);
        }
    }

    @Override
    public List<Article> findByUser(User user) {
        return articleRepository.findByUser(user);
    }

    @Override
    public Article findById(Integer id) {
        return articleRepository.getOne(id);
    }

    @Override
    public Article findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    @Override
    public List<Article> filter(Integer skip, Integer limit, String q, Integer authorId, String fieldName, String order, Integer authorIdFromRequest) {
        skip = (skip != null ? skip : 0);
        limit = (limit != null ? limit : 0);
        authorId = (authorId != null ? authorId : authorIdFromRequest);
        fieldName = (fieldName != null ? fieldName : "id");
        if(order != null) {
            order = (order.equalsIgnoreCase("asc") ? "asc" : "desc");
        }else{
            order = "asc";
        }
        
        List<Article> articles;

        if (q == null) {
            articles = articleRepository.filterWithoutQ(authorId, fieldName, order, skip, limit);
        } else {
            articles = articleRepository.filterWithQ(q, authorId, fieldName, order, skip, limit);
        }

        if (order.equals("asc")) {
            return articles.stream().sorted(ArticleComparator.valueOf(fieldName.toUpperCase(Locale.ROOT))).collect(Collectors.toList());
        } else {
            return articles.stream().sorted(ArticleComparator.valueOf(fieldName.toUpperCase(Locale.ROOT)).reversed()).collect(Collectors.toList());
        }
    }

    @Override
    public List<Article> findByTagId(Integer id) {
        return articleRepository.findByTagId(id);
    }
}
