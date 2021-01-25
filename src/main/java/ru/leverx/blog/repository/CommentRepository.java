package ru.leverx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leverx.blog.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
