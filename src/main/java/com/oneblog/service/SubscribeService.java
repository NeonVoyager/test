package com.oneblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oneblog.entity.Subscribe;

import java.util.List;

public interface SubscribeService extends IService<Subscribe> {
    List<Subscribe> getVerifiedSubscribes();
}

