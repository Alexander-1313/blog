package ru.leverx.blog.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.leverx.blog.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean save(User user);

    void delete(User user);

    List<User> findAll();

    User getById(Integer id);

    User findByEmail(String email);

    User findByPasswordAndEmail(String password, String email);

}
