package com.oneblog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oneblog.common.Result;
import com.oneblog.entity.KeywordLink;
import com.oneblog.service.KeywordLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台关键字链接管理控制器
 */
@RestController
@RequestMapping("/admin/keyword-link")
public class AdminKeywordLinkController {

    @Autowired
    private KeywordLinkService keywordLinkService;

    /**
     * 获取关键字链接列表
     */
    @GetMapping("/list")
    public Result<IPage<KeywordLink>> getKeywordLinkList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<KeywordLink> page = new Page<>(current, size);
        IPage<KeywordLink> result = keywordLinkService.page(page);
        return Result.success(result);
    }

    /**
     * 保存关键字链接
     */
    @PostMapping
    public Result<Void> saveKeywordLink(@RequestBody KeywordLink keywordLink) {
        if (keywordLink.getId() == null) {
            keywordLink.setCreateTime(LocalDateTime.now());
        }
        keywordLink.setUpdateTime(LocalDateTime.now());
        keywordLinkService.saveOrUpdate(keywordLink);
        return Result.success(null);
    }

    /**
     * 删除关键字链接
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteKeywordLink(@PathVariable Long id) {
        keywordLinkService.removeById(id);
        return Result.success(null);
    }
}

