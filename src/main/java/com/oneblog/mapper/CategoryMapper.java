package com.oneblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oneblog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}

