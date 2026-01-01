package com.oneblog.listener;

import com.oneblog.entity.Article;
import com.oneblog.entity.Subscribe;
import com.oneblog.service.EmailService;
import com.oneblog.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文章发布监听器（用于发送邮件通知）
 */
@Component
public class ArticlePublishListener {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private EmailService emailService;

    @Async
    public void onArticlePublished(Article article) {
        if (article.getStatus() == 1) { // 已发布
            List<Subscribe> subscribes = subscribeService.getVerifiedSubscribes();
            for (Subscribe subscribe : subscribes) {
                emailService.sendArticleNotification(article, subscribe.getEmail());
            }
        }
    }
}

