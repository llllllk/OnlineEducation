package com.nwu.base.client.impl;

import com.nwu.base.client.CloudClient;
import com.nwu.common.entity.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class CloudClientImpl implements CloudClient {
    @Override
    public CommonResult redisExpire(String key, long time) {
        return null;
    }

    @Override
    public CommonResult redisHasKey(String key) {
        return null;
    }

    @Override
    public CommonResult redisSetStr(String key, String value) {
        return null;
    }

    @Override
    public CommonResult redisGetStr(String key) {
        return null;
    }

    @Override
    public CommonResult redisSetStrWithTime(String key, String value, long timeOut) {
        return null;
    }

    @Override
    public CommonResult redisGetAndSetStr(String key, String value) {
        return null;
    }

    @Override
    public CommonResult redisDelete(String... key) {
        return null;
    }
}
