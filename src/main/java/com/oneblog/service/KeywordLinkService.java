package com.oneblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oneblog.entity.KeywordLink;

import java.util.List;

public interface KeywordLinkService extends IService<KeywordLink> {
    List<KeywordLink> getActiveKeywordLinks();
    String processArticleContent(String content);
}

