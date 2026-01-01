package com.oneblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oneblog.common.Result;
import com.oneblog.entity.Article;
import com.oneblog.entity.Subscribe;
import com.oneblog.service.EmailService;
import com.oneblog.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 订阅控制器
 */
@RestController
@RequestMapping("/api/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private EmailService emailService;

    /**
     * 订阅邮箱提醒
     */
    @PostMapping
    public Result<Void> subscribe(@RequestParam String email) {
        String verifyCode = UUID.randomUUID().toString().substring(0, 6);
        
        Subscribe subscribe = new Subscribe();
        subscribe.setEmail(email);
        subscribe.setStatus(0);
        subscribe.setVerifyCode(verifyCode);
        subscribeService.save(subscribe);
        
        emailService.sendSubscribeVerification(email, verifyCode);
        return Result.success(null);
    }

    /**
     * 验证订阅
     */
    @PostMapping("/verify")
    public Result<Void> verifySubscribe(@RequestParam String email, @RequestParam String verifyCode) {
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getEmail, email)
               .eq(Subscribe::getVerifyCode, verifyCode);
        Subscribe subscribe = subscribeService.getOne(wrapper);
        
        if (subscribe != null) {
            subscribe.setStatus(1);
            subscribeService.updateById(subscribe);
            return Result.success(null);
        }
        return Result.error("验证码错误");
    }
}

