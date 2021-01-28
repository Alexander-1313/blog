package ru.leverx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Status;
import ru.leverx.blog.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByUser(User user);

    Article findByIdAndUser(Integer id, User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM Article a WHERE a.id = :id")
    void deleteArticle(Integer id);
}
