package ru.leverx.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.configuration.jwt.JwtProvider;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.UserService;
import ru.leverx.blog.util.RequestUtil;
import ru.leverx.blog.util.View;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class RegistrationController {

    private UserService service;
    private JwtProvider jwtProvider;
    private RequestUtil requestUtil;

    @Autowired
    public RegistrationController(UserService service, JwtProvider jwtProvider, RequestUtil requestUtil) {
        this.service = service;
        this.jwtProvider = jwtProvider;
        this.requestUtil = requestUtil;
    }

    @JsonView(View.UI.class)
    @GetMapping
    public List<User> showAll() {
        return service.findAll();
    }

    @PostMapping("/registration")
    public void registerUser(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {
        service.save(new User(firstName, lastName, password, email, new Date()));
    }

    @GetMapping("/auth")
    public String auth(@RequestParam("email") String email,
                     @RequestParam("password") String password){

        User user = service.findByPasswordAndEmail(password, email);
        String token = jwtProvider.generateToken(user.getEmail());
        return token;
    }

    @JsonView(View.UI.class)
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id,
                        HttpServletRequest request){

        if(requestUtil.isConfirmUser(request, id)){
            return service.getById(Integer.parseInt(id));
        }
        return null;
    }

    /**
     * auth/forgot_password/{email} POST
     * auth/reset/{new paswrod}
     * auth/check_code/{code}
     */


}
