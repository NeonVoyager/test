package com.oneblog.controller.admin;

import com.oneblog.common.Result;
import com.oneblog.entity.Category;
import com.oneblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台分类管理控制器
 */
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> getCategoryList() {
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    @PostMapping
    public Result<Void> saveCategory(@RequestBody Category category) {
        if (category.getId() == null) {
            category.setCreateTime(LocalDateTime.now());
        }
        category.setUpdateTime(LocalDateTime.now());
        category.setDeleted(0);
        categoryService.saveOrUpdate(category);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        if (category != null) {
            category.setDeleted(1);
            categoryService.updateById(category);
        }
        return Result.success(null);
    }
}

