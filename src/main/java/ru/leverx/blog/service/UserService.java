package ru.leverx.blog.service;

import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.User;

import java.util.List;

public interface UserService {

    User findByEmail(String email);

    User findById(Integer id);

    List<User> findAll();

    boolean save(User user);

    User findByPasswordAndEmail(String password, String email);

    void registerByEmail(String email);

    void resetPasswordByEmail(String email, String password);

    User findByArticle(Article article);
}
