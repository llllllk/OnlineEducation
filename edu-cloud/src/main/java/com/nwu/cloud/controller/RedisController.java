package com.nwu.cloud.controller;

import com.nwu.common.entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cloud/redis")
@CrossOrigin
public class RedisController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //指定缓存失效时间
    @PostMapping("/expire")
    public CommonResult redisExpire(@RequestParam("key") String key,@RequestParam("time") long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return CommonResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }

    }
    //判断key是否存在
    @PostMapping("/hasKey")
    public CommonResult redisHasKey(@RequestParam("key") String key) {
        try {
            if (key==null) return CommonResult.ok();
            boolean flag=redisTemplate.hasKey(key);
            if (flag) CommonResult.ok().data("exist",true);
            else return CommonResult.ok().data("exist",false);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.ok();
    }
    //删除一个或多个键
    @PostMapping(value = "/delete")
    public CommonResult redisDelete(@RequestBody String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
        return CommonResult.ok();
    }
    //获取值
    @PostMapping("/getStr")
    public CommonResult redisGetStr(@RequestParam("key") final String key) {
        if (key==null){
            return CommonResult.ok().data("data",null);
        }
        String value=String.valueOf(redisTemplate.opsForValue().get(key));
        System.out.println(value);
        return CommonResult.ok().data("data",value);
    }
    //设置缓存
    @PostMapping("/setStr")
    public CommonResult redisSetStr(@RequestParam("key") final String key, @RequestParam("value") final String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.ok();
    }
    //写入缓存带失效时间
    @PostMapping("/setStrWithTime")
    public boolean redisSetStrWithTime(@RequestParam("key") final String key,@RequestParam("value") final String value,@RequestParam("timeOut") final long timeOut) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //更新缓存
    @PostMapping("/updateStr")
    public boolean redisGetAndSetStr(@RequestParam("key") final String key,@RequestParam("value") final String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
