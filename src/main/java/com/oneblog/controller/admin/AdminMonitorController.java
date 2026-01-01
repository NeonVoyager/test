package com.oneblog.controller.admin;

import com.oneblog.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统监控控制器
 */
@RestController
@RequestMapping("/admin/monitor")
public class AdminMonitorController {

    /**
     * 获取系统信息
     */
    @GetMapping("/system")
    public Result<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        
        // JVM信息
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        
        // 内存信息
        long totalMemory = memoryMXBean.getHeapMemoryUsage().getMax();
        long usedMemory = memoryMXBean.getHeapMemoryUsage().getUsed();
        long freeMemory = totalMemory - usedMemory;
        
        Map<String, Object> memory = new HashMap<>();
        memory.put("total", totalMemory / 1024 / 1024); // MB
        memory.put("used", usedMemory / 1024 / 1024); // MB
        memory.put("free", freeMemory / 1024 / 1024); // MB
        memory.put("usage", (usedMemory * 100 / totalMemory)); // %
        
        info.put("memory", memory);
        
        // JVM信息
        Map<String, Object> jvm = new HashMap<>();
        jvm.put("name", runtimeMXBean.getVmName());
        jvm.put("version", runtimeMXBean.getVmVersion());
        jvm.put("uptime", runtimeMXBean.getUptime() / 1000); // 秒
        
        info.put("jvm", jvm);
        
        // CPU信息
        Runtime runtime = Runtime.getRuntime();
        int processors = runtime.availableProcessors();
        Map<String, Object> cpu = new HashMap<>();
        cpu.put("processors", processors);
        
        info.put("cpu", cpu);
        
        return Result.success(info);
    }

    /**
     * 获取数据库连接池信息
     */
    @GetMapping("/datasource")
    public Result<Map<String, Object>> getDatasourceInfo() {
        Map<String, Object> info = new HashMap<>();
        // 这里可以集成Druid监控
        // DruidStatManagerFacade.getInstance().getDataSourceStatDataList()
        info.put("message", "需要集成Druid监控");
        return Result.success(info);
    }
}

