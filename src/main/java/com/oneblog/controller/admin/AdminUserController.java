package com.oneblog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oneblog.common.Result;
import com.oneblog.entity.User;
import com.oneblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台用户管理控制器
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<User> page = new Page<>(current, size);
        IPage<User> result = userService.page(page);
        return Result.success(result);
    }

    @PostMapping
    public Result<Void> saveUser(@RequestBody User user) {
        if (user.getId() == null) {
            user.setCreateTime(LocalDateTime.now());
            // 密码加密（实际应该使用BCrypt等加密）
            // user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setUpdateTime(LocalDateTime.now());
        user.setDeleted(0);
        userService.saveOrUpdate(user);
        return Result.success(null);
    }

    @PostMapping("/reset-password/{id}")
    public Result<Void> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        User user = userService.getById(id);
        if (user != null) {
            // 密码加密
            // user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateById(user);
        }
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setDeleted(1);
            userService.updateById(user);
        }
        return Result.success(null);
    }
}

