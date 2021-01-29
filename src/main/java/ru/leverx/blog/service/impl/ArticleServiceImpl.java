package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.stereotype.Service;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.repository.ArticleRepository;
import ru.leverx.blog.repository.UserRepository;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.CommentService;
import ru.leverx.blog.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository, UserService service) {
        this.articleRepository = repository;
        this.userService = service;
    }

    @Override
    public void deleteById(Integer userId, Integer articleId) {
        User user = userService.findById(userId);

        Article article = articleRepository.findByIdAndUser(articleId, user);

        if(article != null) {
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
    public void updateById(Integer articleId, Integer userId, String title, String text, String status) {
        User user = userService.findById(userId);
        Article article = articleRepository.findByIdAndUser(articleId, user);
        if (article != null) {
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

            articleRepository.save(article);
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
    public List<Article> filter(Integer skip, Integer limit, String q, Integer authorId, String fieldName, String order, Integer authorIdFromRequest) {
        skip = (skip != null ? skip : 0);
        limit = (limit != null ? limit : 0);
        authorId = (authorId != null ? authorId : authorIdFromRequest);
        fieldName = (fieldName != null ? fieldName : "id");
        order = (order.equalsIgnoreCase("asc") ? "asc" : "desc");

        if (q == null) {
            return articleRepository.filterWithoutQ(authorId, fieldName, order, skip, limit);
        }else{
            return articleRepository.filterWithQ(q, authorId, fieldName, order, skip, limit);
        }
    }
}
