package ru.leverx.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import ru.leverx.blog.configuration.jwt.JwtProvider;
import ru.leverx.blog.entity.Article;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.ArticleService;
import ru.leverx.blog.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.util.StringUtils.hasText;
import static ru.leverx.blog.configuration.jwt.JwtFilter.AUTHORIZATION;

@Service
public class RequestUtil {

    private JwtProvider jwtProvider;
    private UserService userService;
    private Jedis jedis;
    private ArticleService articleService;

    @Autowired
    public RequestUtil(JwtProvider jwtProvider, UserService userService, Jedis jedis, ArticleService articleService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.jedis = jedis;
        this.articleService = articleService;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public boolean isConfirmUser(HttpServletRequest request, String id){
        String requestToken = getTokenFromRequest(request);
        String generatedTokenFromRequestId = jwtProvider.generateToken(userService.findById(Integer.parseInt(id)).getEmail());
        return requestToken.equals(generatedTokenFromRequestId);
    }

    public boolean hasAccess(HttpServletRequest request){
        String token = getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userService.findByEmail(email);
        return user != null;
    }

    public boolean hasArticle(HttpServletRequest request, Integer articleId){
        String token = getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userService.findByEmail(email);
        Article article = articleService.findById(articleId);
        User userDb = userService.findByArticle(article);

        return userDb == user;
    }

    public Integer getUserIdByRequest(HttpServletRequest request){
        String token = getTokenFromRequest(request);
        String email = jwtProvider.getLoginFromToken(token);
        User user = userService.findByEmail(email);
        if(user != null){
            return user.getId();
        }

        return -1;
    }

    public Integer strToInt(String str){
        if(str != null && str.matches("[-+]?\\d+")){
            return Integer.parseInt(str);
        }
        return null;
    }

    public boolean isDateAvailable(String strDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(strDate, formatter);
        LocalDateTime newDate = date.plusHours(24);
        return newDate.isAfter(LocalDateTime.now());
    }
}
