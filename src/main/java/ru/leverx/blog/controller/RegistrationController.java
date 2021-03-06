package ru.leverx.blog.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.blog.configuration.jwt.JwtProvider;
import ru.leverx.blog.entity.User;
import ru.leverx.blog.service.MailService;
import ru.leverx.blog.service.RedisService;
import ru.leverx.blog.service.UserService;
import ru.leverx.blog.util.RequestUtil;
import ru.leverx.blog.util.View;

import javax.servlet.http.HttpServletRequest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class RegistrationController {

    private UserService service;
    private JwtProvider jwtProvider;
    private RequestUtil requestUtil;
    private RedisService redisService;
    private MailService mailService;

    @Autowired
    public RegistrationController(UserService service, JwtProvider jwtProvider, RequestUtil requestUtil, RedisService redisService, MailService mailService) {
        this.service = service;
        this.jwtProvider = jwtProvider;
        this.requestUtil = requestUtil;
        this.redisService = redisService;
        this.mailService = mailService;
    }

    @JsonView(View.UI.class)
    @GetMapping
    public List<User> showAll() {
        return service.findAll();
    }

    @PostMapping("/registration")
    public String registerUser(@RequestBody User user) {

        String email = user.getEmail();
        User dbUser = service.findByEmail(email);

        if (dbUser == null) {
            user.setCreatedAt(new Date());
            service.save(user);
            String token = jwtProvider.generateToken(email);
            redisService.save(token, email);
            Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            redisService.save(email, format.format(new Date()));
            mailService.sendEmail(email, token, "/confirm");
            return token;
        }

        return "";
    }

    @GetMapping("/auth")
    public String auth(@RequestBody User user){

        User userDb = service.findByPasswordAndEmail(user.getPassword(), user.getEmail());

        if(userDb != null && userDb.isEnabled()) {
            String token = jwtProvider.generateToken(user.getEmail());
            return token;
        }

        return "";
    }

    @JsonView(View.UI.class)
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id,
                        HttpServletRequest request){

        if(requestUtil.isConfirmUser(request, id)){
            return service.findById(Integer.parseInt(id));
        }
        return null;
    }


    @PostMapping("/auth/confirm")
    public void confirmRegistration(@RequestParam("token") String token){

        String email = redisService.getByToken(token);
        String strDate = redisService.getDateByEmail(email);

        if(email != null && requestUtil.isDateAvailable(strDate)){
            service.registerByEmail(email);
        }
    }

    @PostMapping("/auth/forgot_password")
    public void forgotPassword(@RequestParam("email") String email){
        if(email != null){
            mailService.sendEmail(email, jwtProvider.generateToken(email), "/reset");
        }
    }

    @PostMapping("/auth/reset")
    public void resetPassword(@RequestParam("token") String token,
                              @RequestParam("new_password") String newPassword){

        String email = redisService.getByToken(token);

        if(email != null){
            service.resetPasswordByEmail(email, newPassword);
        }
    }

}
