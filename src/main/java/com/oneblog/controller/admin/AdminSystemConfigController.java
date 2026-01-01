package com.oneblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oneblog.common.Result;
import com.oneblog.entity.SystemConfig;
import com.oneblog.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台系统配置控制器
 */
@RestController
@RequestMapping("/admin/system-config")
public class AdminSystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping("/all")
    public Result<Map<String, String>> getAllConfigs() {
        List<SystemConfig> configs = systemConfigService.list();
        Map<String, String> configMap = new HashMap<>();
        for (SystemConfig config : configs) {
            configMap.put(config.getConfigKey(), config.getConfigValue());
        }
        return Result.success(configMap);
    }

    @PostMapping
    public Result<Void> saveConfig(@RequestBody SystemConfig config) {
        LambdaQueryWrapper<SystemConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemConfig::getConfigKey, config.getConfigKey());
        SystemConfig existing = systemConfigService.getOne(wrapper);
        
        if (existing != null) {
            existing.setConfigValue(config.getConfigValue());
            existing.setUpdateTime(LocalDateTime.now());
            systemConfigService.updateById(existing);
        } else {
            config.setUpdateTime(LocalDateTime.now());
            systemConfigService.save(config);
        }
        return Result.success(null);
    }
}

