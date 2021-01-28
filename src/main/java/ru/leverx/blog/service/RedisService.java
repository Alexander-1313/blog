package ru.leverx.blog.service;

public interface RedisService {

    void save(String key, String value);

    String getByToken(String token);
}
