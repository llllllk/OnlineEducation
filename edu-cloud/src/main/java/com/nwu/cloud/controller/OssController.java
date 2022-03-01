package com.nwu.cloud.controller;

import com.nwu.cloud.service.OssService;
import com.nwu.common.entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin //跨域
@RestController
@RequestMapping("/cloud/oss/file")
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping(value = "upload")
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        String uploadUrl = ossService.upload(file);
        return CommonResult.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
