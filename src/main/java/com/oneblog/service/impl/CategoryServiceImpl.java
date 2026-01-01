package com.oneblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.Category;
import com.oneblog.mapper.CategoryMapper;
import com.oneblog.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}

