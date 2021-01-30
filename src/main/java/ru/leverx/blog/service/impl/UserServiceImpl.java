package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.repository.UserRepository;
import ru.leverx.blog.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public User findByPasswordAndEmail(String password, String email) {
        User user = findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void registerByEmail(String email) {
        User user = findByEmail(email);

        if(user != null) {
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    @Override
    public void resetPasswordByEmail(String email, String password) {
        User user = findByEmail(email);

        if(user != null && user.isEnabled()) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }

    @Override
    public User findByArticle(Article article) {
        return userRepository.findByArticle(article);
    }
}
