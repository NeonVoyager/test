package com.oneblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.Subscribe;
import com.oneblog.mapper.SubscribeMapper;
import com.oneblog.service.SubscribeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeServiceImpl extends ServiceImpl<SubscribeMapper, Subscribe> implements SubscribeService {

    @Override
    public List<Subscribe> getVerifiedSubscribes() {
        LambdaQueryWrapper<Subscribe> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subscribe::getStatus, 1);
        return this.list(wrapper);
    }
}

