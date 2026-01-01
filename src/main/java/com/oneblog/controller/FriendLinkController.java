package com.oneblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oneblog.common.Result;
import com.oneblog.entity.FriendLink;
import com.oneblog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 友情链接控制器
 */
@RestController
@RequestMapping("/api/friend-link")
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    @GetMapping("/list")
    public Result<List<FriendLink>> getFriendLinkList() {
        LambdaQueryWrapper<FriendLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendLink::getStatus, 1)
               .eq(FriendLink::getDeleted, 0)
               .orderByAsc(FriendLink::getSort);
        List<FriendLink> links = friendLinkService.list(wrapper);
        return Result.success(links);
    }

    @PostMapping("/apply")
    public Result<Void> applyFriendLink(@RequestBody FriendLink friendLink) {
        friendLink.setStatus(0); // 待审核
        friendLinkService.save(friendLink);
        return Result.success(null);
    }
}

