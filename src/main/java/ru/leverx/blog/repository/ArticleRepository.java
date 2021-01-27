package ru.leverx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findArticleByStatus(Status status);

    List<Article> findArticleByUser(User user);

    List<Article> findArticleByTitleAndUser(String title, User user);

    Article findArticleByIdAndUser(Integer id, User user);

    @Transactional
    void deleteArticlesByIdAndUser(Integer id, User user);

    @Transactional
    void deleteArticlesById(Integer id);
}
