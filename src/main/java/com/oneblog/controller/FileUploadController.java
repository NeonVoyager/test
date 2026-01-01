package com.oneblog.controller;

import com.oneblog.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${file.upload.path:uploads}")
    private String uploadPath;

    @Value("${file.upload.url:http://localhost:8080/uploads}")
    private String uploadUrl;

    /**
     * 上传图片
     */
    @PostMapping("/image")
    public Result<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return Result.error("只能上传图片文件");
        }

        try {
            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;
            
            // 创建日期目录
            String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            File dir = new File(uploadPath + "/" + dateDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            File dest = new File(dir, fileName);
            file.transferTo(dest);

            // 返回文件URL
            String fileUrl = uploadUrl + "/" + dateDir + "/" + fileName;
            Map<String, String> data = new HashMap<>();
            data.put("url", fileUrl);
            data.put("filename", fileName);

            return Result.success(data);
        } catch (IOException e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 上传文件
     */
    @PostMapping("/file")
    public Result<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        try {
            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;
            
            // 创建日期目录
            String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            File dir = new File(uploadPath + "/" + dateDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            File dest = new File(dir, fileName);
            file.transferTo(dest);

            // 返回文件URL
            String fileUrl = uploadUrl + "/" + dateDir + "/" + fileName;
            Map<String, String> data = new HashMap<>();
            data.put("url", fileUrl);
            data.put("filename", fileName);

            return Result.success(data);
        } catch (IOException e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }
}

