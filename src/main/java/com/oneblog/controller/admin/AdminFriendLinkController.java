package com.oneblog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oneblog.common.Result;
import com.oneblog.entity.FriendLink;
import com.oneblog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 后台友情链接管理控制器
 */
@RestController
@RequestMapping("/admin/friend-link")
public class AdminFriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    @GetMapping("/list")
    public Result<IPage<FriendLink>> getFriendLinkList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<FriendLink> page = new Page<>(current, size);
        IPage<FriendLink> result = friendLinkService.page(page);
        return Result.success(result);
    }

    @PostMapping("/audit/{id}")
    public Result<Void> auditFriendLink(@PathVariable Long id, @RequestParam Integer status) {
        FriendLink friendLink = friendLinkService.getById(id);
        if (friendLink != null) {
            friendLink.setStatus(status);
            friendLink.setUpdateTime(LocalDateTime.now());
            friendLinkService.updateById(friendLink);
        }
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteFriendLink(@PathVariable Long id) {
        FriendLink friendLink = friendLinkService.getById(id);
        if (friendLink != null) {
            friendLink.setDeleted(1);
            friendLinkService.updateById(friendLink);
        }
        return Result.success(null);
    }
}

