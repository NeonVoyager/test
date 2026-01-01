package com.oneblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器（用于Thymeleaf模板）
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/article/{id}")
    public String articleDetail() {
        return "article-detail";
    }

    @GetMapping("/category/{id}")
    public String category() {
        return "category";
    }

    @GetMapping("/tag/{name}")
    public String tag() {
        return "tag";
    }

    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }
}

