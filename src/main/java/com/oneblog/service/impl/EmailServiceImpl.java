package com.oneblog.service.impl;

import com.oneblog.entity.Article;
import com.oneblog.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async
    public void sendArticleNotification(Article article, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("新文章发布：" + article.getTitle());
        message.setText("您好！\n\n博主发布了新文章《" + article.getTitle() + "》\n\n" +
                       "摘要：" + article.getSummary() + "\n\n" +
                       "点击查看：http://your-domain.com/article/" + article.getId());
        mailSender.send(message);
    }

    @Override
    @Async
    public void sendSubscribeVerification(String email, String verifyCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("订阅验证");
        message.setText("您好！\n\n您的验证码是：" + verifyCode + "\n\n请在网站中输入此验证码完成订阅。");
        mailSender.send(message);
    }
}

