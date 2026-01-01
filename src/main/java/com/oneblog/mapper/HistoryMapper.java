package com.oneblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oneblog.entity.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper extends BaseMapper<History> {
    List<History> selectByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);
    void deleteByUserId(@Param("userId") Long userId);
}

