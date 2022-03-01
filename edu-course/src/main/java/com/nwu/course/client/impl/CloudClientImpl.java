package com.nwu.course.client.impl;

import com.nwu.common.entity.CommonResult;
import com.nwu.course.client.CloudClient;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CloudClientImpl implements CloudClient {
    @Override
    public CommonResult removeVideo(String videoId) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult removeVideoList(List<String> videoIdList) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult redisExpire(String key, long time) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult redisHasKey(String key) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult redisSetStr(String key, String value) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult redisGetStr(String key) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult redisSetStrWithTime(String key, String value, long timeOut) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult redisGetAndSetStr(String key, String value) {
        return CommonResult.error().message("time out");
    }
    @Override
    public CommonResult redisDelete(String... key) {
        return CommonResult.error().message("time out");
    }
}
