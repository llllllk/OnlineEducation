package com.nwu.cloud.controller;


import com.nwu.cloud.service.VodService;
import com.nwu.common.entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cloud/vod")
public class VodController {

    @Autowired
    private VodService vodService;

    @GetMapping(value = "location/{id}")
    public CommonResult getLocation(@PathVariable("id") String id){
        String location = vodService.getVideoLocation(id);
        return CommonResult.ok().data("location",location);
    }

    @PostMapping(value = "upload")
    public CommonResult uploadVideo(@RequestParam("file") MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return CommonResult.ok().message("视频上传成功").data("videoId", videoId);
    }

    @DeleteMapping(value = "delete/{videoId}")
    public CommonResult removeVideo(@PathVariable("videoId") String videoId){
        boolean b = vodService.removeVideo(videoId);
        if (b) return CommonResult.ok().message("视频删除成功");
        else return CommonResult.error();
    }

    @DeleteMapping(value = "deleteBatch")
    public CommonResult removeVideoList(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeVideoList(videoIdList);
        return CommonResult.ok().message("视频删除成功");
    }

    @GetMapping(value = "getAuth/{videoId}")
    public CommonResult getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        String playAuth = vodService.getVideoPlayAuth(videoId);
        System.out.println(playAuth);
        return CommonResult.ok().data("playAuth",playAuth);
    }
}
