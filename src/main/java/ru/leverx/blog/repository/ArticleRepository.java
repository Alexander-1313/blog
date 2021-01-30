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

    Article findByTitle(String title);

    @Modifying
    @Query(value = "SELECT * FROM article a inner join article_tag t on a.id = t.article_id WHERE t.tag_id = ?1", nativeQuery = true)
    List<Article> findByTagId(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Article a WHERE a.id = :id")
    void deleteArticle(Integer id);

    @Modifying
    @Query(value = "SELECT * FROM article a WHERE a.author_id = ?1 AND a.status = \"PUBLIC\" ORDER BY ?2 ?3 LIMIT ?4, ?5", nativeQuery = true)
    List<Article> filterWithoutQ(Integer author, String fieldName, String order, Integer skip, Integer limit);

    @Modifying
    @Query(value = "SELECT * FROM article a WHERE a.title = ?1 AND a.author_id = ?2 AND a.status = \"PUBLIC\" ORDER BY ?3 ?4 LIMIT ?5, ?6", nativeQuery = true)
    List<Article> filterWithQ(String title, Integer author, String fieldName, String order, Integer skip, Integer limit);
}
