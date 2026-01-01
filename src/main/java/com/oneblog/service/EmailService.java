package com.oneblog.service;

import com.oneblog.entity.Article;

public interface EmailService {
    void sendArticleNotification(Article article, String email);
    void sendSubscribeVerification(String email, String verifyCode);
}

