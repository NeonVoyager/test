package com.oneblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oneblog.entity.FriendLink;
import com.oneblog.mapper.FriendLinkMapper;
import com.oneblog.service.FriendLinkService;
import org.springframework.stereotype.Service;

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {
}

