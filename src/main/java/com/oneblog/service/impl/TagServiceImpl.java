package com.oneblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.Tag;
import com.oneblog.mapper.TagMapper;
import com.oneblog.service.TagService;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
}

