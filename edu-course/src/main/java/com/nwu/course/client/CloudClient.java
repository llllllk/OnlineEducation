package com.nwu.course.client;


import com.nwu.common.entity.CommonResult;
import com.nwu.course.client.impl.CloudClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-cloud",fallback = CloudClientImpl.class)
@Component
public interface CloudClient {
    @DeleteMapping(value = "cloud/vod/delete/{videoId}")
    public CommonResult removeVideo(@PathVariable("videoId") String videoId);
    @DeleteMapping(value = "cloud/vod/deleteBatch")
    public CommonResult removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);

    @PostMapping(value ="cloud/redis/expire")
    public CommonResult redisExpire(@RequestParam("key") String key,@RequestParam("time") long time);
    @PostMapping(value ="cloud/redis/hasKey")
    public CommonResult redisHasKey(@RequestParam("key") String key);
    @PostMapping(value ="cloud/redis/setStr")
    public CommonResult redisSetStr(@RequestParam("key") final String key, @RequestParam("value") final String value);
    @PostMapping(value ="cloud/redis/getStr")
    public CommonResult redisGetStr(@RequestParam("key") final String key);
    @PostMapping(value ="cloud/redis/setStrWithTime")
    public CommonResult redisSetStrWithTime(@RequestParam("key") final String key,@RequestParam("value") final String value,@RequestParam("timeOut") final long timeOut);
    @PostMapping(value ="cloud/redis/updateStr")
    public CommonResult redisGetAndSetStr(@RequestParam("key") final String key,@RequestParam("value") final String value);
    @PostMapping(value = "/delete")
    public CommonResult redisDelete(@RequestBody String... key);
}
