package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import ru.leverx.blog.service.RedisService;
import ru.leverx.blog.service.UserService;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private Jedis jedis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String key, String value) {
        jedis.set(key, value);
    }

    @Override
    public String getByToken(String token) {
        return jedis.get(token);
    }

    @Override
    public String getDateByEmail(String email){
        return jedis.get(email);
    }


}
