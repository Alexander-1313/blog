package ru.leverx.blog.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.leverx.blog.configuration.jwt.JwtProvider;
import ru.leverx.blog.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;
import static ru.leverx.blog.configuration.jwt.JwtFilter.AUTHORIZATION;

@Service
public class RequestUtil {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    public static String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public static String generateToken(String email) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, "rybak")
                .compact();
    }

    public boolean isConfirmUser(HttpServletRequest request, String id){
        String requestToken = RequestUtil.getTokenFromRequest(request);
        String generatedTokenFromRequestId = jwtProvider.generateToken(userService.getById(Integer.parseInt(id)).getEmail());
        return requestToken.equals(generatedTokenFromRequestId);
    }
}