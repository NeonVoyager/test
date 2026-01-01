package com.oneblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oneblog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}

