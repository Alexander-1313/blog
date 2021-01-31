package ru.leverx.blog.repository;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.UserService;

@SpringBootTest
@ActiveProfiles("integration-test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    User user;

    @BeforeEach
    public void before(){
        user = new User();
        user.setEmail("test@mail.ru");
        user.setPassword("test");
        user.setId(0);
        userService.save(user);
    }

    @Test
    void findByEmail() {
        User userFromRepository = userRepository.findByEmail("test@mail.ru");
        Assert.assertEquals(user.getEmail(), userFromRepository.getEmail());
    }


}