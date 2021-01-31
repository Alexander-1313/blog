package ru.leverx.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import ru.leverx.blog.service.MailService;
import ru.leverx.blog.util.StringConstant;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String email, String token, String uri) {

        Properties props = new Properties();
        props.put("mail.smtp.host" , "smtp.mail.ru");
        props.put("mail.stmp.user" , "username");

        //To use TLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.password", "password");
        //To use SSL
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");


        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                StringConstant.EMAIL_NAME, StringConstant.EMAIL_PASSWORD);
                    }
                });
        String to = email;
        String from = StringConstant.EMAIL_NAME;
        String subject = "Registration";
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText("http://localhost:8080/auth" + uri + "/?token=" + token);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.mail.ru" , 465 , "username", "password");
            transport.send(msg);
            System.out.println("fine!!");
        }
        catch(Exception exc) {
            System.out.println(exc);
        }
    }
}
