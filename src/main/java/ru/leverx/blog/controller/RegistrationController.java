package ru.leverx.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.UserService;

import java.util.Date;
import java.util.List;

@RestController
public class RegistrationController {

    @Autowired
    private UserService service;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private final JavaMailSender javaMailSender = new JavaMailSenderImpl();

    @GetMapping("/")
    public List<User> showAll() {
        return service.findAll();
    }

    @PostMapping("/add")
    public void registerUser(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {
        sendEmail(email);
        service.save(new User(firstName, lastName, password, email, new Date()));
    }

    private void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World n Spring Boot Email");

        javaMailSender.send(msg);

    }

}
