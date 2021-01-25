package ru.leverx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Transactional
    void deleteCommentById(Integer id);

    List<Comment> findCommentByArticle(Article article);

    List<Comment> findCommentByCommentUserAndArticle(User commentUser, Article article);

}
