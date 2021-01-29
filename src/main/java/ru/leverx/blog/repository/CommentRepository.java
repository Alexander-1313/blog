package ru.leverx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment findByIdAndCommentUserAndArticle(Integer id, User user, Article article);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :id")
    void deleteComment(Integer id);

    @Modifying
    @Query(value = "SELECT * FROM comment c WHERE c.author_id = ?1 ORDER BY ?2 ?3 LIMIT ?4, ?5", nativeQuery = true)
    List<Comment> filterWithoutQ(Integer authorId, String fieldName, String order, Integer skip, Integer limit);

    @Modifying
    @Query(value = "SELECT * FROM comment c WHERE c.author_id = ?1 AND c.post_id = ?2 ORDER BY ?3 ?4 LIMIT ?5, ?6", nativeQuery = true)
    List<Comment> filterWithQ(Integer authorId, Integer postId, String fieldName, String order, Integer skip, Integer limit);
}
