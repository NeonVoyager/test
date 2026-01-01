package com.oneblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.KeywordLink;
import com.oneblog.mapper.KeywordLinkMapper;
import com.oneblog.service.KeywordLinkService;
import com.oneblog.util.KeywordLinkUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordLinkServiceImpl extends ServiceImpl<KeywordLinkMapper, KeywordLink> implements KeywordLinkService {

    @Override
    public List<KeywordLink> getActiveKeywordLinks() {
        LambdaQueryWrapper<KeywordLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KeywordLink::getStatus, 1);
        return this.list(wrapper);
    }

    @Override
    public String processArticleContent(String content) {
        List<KeywordLink> keywordLinks = getActiveKeywordLinks();
        return KeywordLinkUtil.replaceKeywords(content, keywordLinks);
    }
}

