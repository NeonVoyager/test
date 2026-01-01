package com.oneblog.controller.admin;

import com.oneblog.common.Result;
import com.oneblog.entity.Tag;
import com.oneblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台标签管理控制器
 */
@RestController
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public Result<List<Tag>> getTagList() {
        List<Tag> tags = tagService.list();
        return Result.success(tags);
    }

    @PostMapping
    public Result<Void> saveTag(@RequestBody Tag tag) {
        if (tag.getId() == null) {
            tag.setCreateTime(LocalDateTime.now());
        }
        tag.setUpdateTime(LocalDateTime.now());
        tag.setDeleted(0);
        tagService.saveOrUpdate(tag);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteTag(@PathVariable Long id) {
        Tag tag = tagService.getById(id);
        if (tag != null) {
            tag.setDeleted(1);
            tagService.updateById(tag);
        }
        return Result.success(null);
    }
}

