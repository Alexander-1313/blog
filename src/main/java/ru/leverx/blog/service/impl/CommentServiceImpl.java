package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.repository.ArticleRepository;
import ru.leverx.blog.repository.CommentRepository;
import ru.leverx.blog.repository.UserRepository;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.CommentService;
import ru.leverx.blog.service.UserService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, ArticleService articleService, UserService userService) {
        this.commentRepository = repository;
        this.articleService = articleService;
        this.userService = userService;
    }

    @Override
    public void save(Comment comment) {
        if(comment != null){
          commentRepository.save(comment);
        }
    }

    @Override
    public Comment findById(Integer id) {
        return commentRepository.getOne(id);
    }

    @Override
    public void deleteCommentByIdAndArticleAndCommentUser(Integer id, Article article, User user) {
        Comment comment = commentRepository.findByIdAndCommentUserAndArticle(id, user, article);

        if(comment != null){
            commentRepository.deleteComment(id);
        }
    }

    @Override
    public List<Comment> filter(Integer skip, Integer limit, String q, Integer authId, String fieldName, String order, Integer authorIdFromRequest) {
        skip = (skip != null ? skip : 0);
        limit = (limit != null ? limit : 0);
        authId = (authId != null ? authId : authorIdFromRequest);
        fieldName = (fieldName != null ? fieldName : "id");
        order = (order.equalsIgnoreCase("asc") ? "asc" : "desc");

        if (q == null) {
            return commentRepository.filterWithoutQ(authId, fieldName, order, skip, limit);
        }else{
            Article article = articleService.findByTitle(q);
            return commentRepository.filterWithQ(authId, article.getId(), fieldName, order, skip, limit);
        }
    }
}
