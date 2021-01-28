package ru.leverx.blog.service;

public interface MailService {

    void sendEmail(String email, String token, String uri);
}
