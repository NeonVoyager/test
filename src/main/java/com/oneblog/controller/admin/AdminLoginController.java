package com.oneblog.controller.admin;

import com.oneblog.common.Result;
import com.oneblog.entity.User;
import com.oneblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台登录控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestParam String username, 
                                             @RequestParam String password,
                                             HttpSession session) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Result.error("用户名和密码不能为空");
        }

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            
            // 登录成功，获取用户信息
            User user = (User) subject.getPrincipal();
            session.setAttribute("user", user);
            
            Map<String, Object> data = new HashMap<>();
            data.put("token", subject.getSession().getId());
            data.put("user", user);
            
            return Result.success("登录成功", data);
        } catch (Exception e) {
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        session.invalidate();
        return Result.success(null);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/current-user")
    public Result<User> getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            return Result.success(user);
        }
        return Result.error("未登录");
    }
}

