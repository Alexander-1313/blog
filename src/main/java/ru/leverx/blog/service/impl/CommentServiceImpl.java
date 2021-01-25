package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.leverx.blog.comparator.CommentComparator;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.Comment;
import ru.leverx.blog.repository.CommentRepository;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.CommentService;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final ArticleService articleService;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, ArticleService articleService) {
        this.repository = repository;
        this.articleService = articleService;
    }

    @Override
    public void save(Comment comment) {
        repository.save(comment);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteCommentById(id);
    }

    @Override
    public Comment findById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Comment> findCommentByArticle(Article article){
        return repository.findCommentByArticle(article);
    }

    @Override
    public List<Comment> filterComments(Map<String, String> allRequestParams, String articleId) {
        Article article = articleService.findById(Integer.parseInt(articleId));
        List<Comment> filteredComments = repository.findCommentByArticle(article);

        if(allRequestParams.get("author") != null){
            filteredComments = filterByAuthorId(filteredComments, Integer.parseInt(allRequestParams.get("author")));
        }
        if(allRequestParams.get("skip") != null){
            filteredComments = filterBySkip(filteredComments, Integer.parseInt(allRequestParams.get("skip")));
        }
        if(allRequestParams.get("limit") != null){
            filteredComments = filterByLimit(filteredComments, Integer.parseInt(allRequestParams.get("limit")));
        }
        if(allRequestParams.get("sort") != null && allRequestParams.get("order").equalsIgnoreCase("desc")){
            filteredComments = sortDescOrder(filteredComments, allRequestParams.get("sort"));
        }else if(allRequestParams.get("sort") != null){
            filteredComments = sortAscOrder(filteredComments, allRequestParams.get("sort"));
        }

        return filteredComments;
    }

    private List<Comment> filterByAuthorId(List<Comment> comments, Integer id){
        return comments.stream().filter(a -> a.getCommentUser().getId().equals(id)).collect(Collectors.toList());
    }

    private List<Comment> filterBySkip(List<Comment> comments, Integer skip){
        return comments.stream().skip(skip).collect(Collectors.toList());
    }

    private List<Comment> filterByLimit(List<Comment> comments, Integer limit){
        return comments.stream().limit(limit).collect(Collectors.toList());
    }

    private List<Comment> sortAscOrder(List<Comment> comments, String sort){
        return comments.stream().sorted(CommentComparator.valueOf(sort)).collect(Collectors.toList());
    }

    private List<Comment> sortDescOrder(List<Comment> comments, String sort){
        return comments.stream().sorted(CommentComparator.valueOf(sort).reversed()).collect(Collectors.toList());
    }

}
