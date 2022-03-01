package com.nwu.cloud.controller;


import com.nwu.cloud.service.MsgService;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/cloud/msg")
public class MsgController {
    @Autowired
    private MsgService msgService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @GetMapping(value = "/send/{phone}")
    public CommonResult code(@PathVariable String phone) {
        String code=redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isBlank(code)){
            return CommonResult.ok().message("请1分钟后再获取");
        }
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msgService.send(phone, param);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code, 120, TimeUnit.SECONDS);
            return CommonResult.ok().message("发送成功");
        } else {
            return CommonResult.error().message("发送短信失败");
        }
    }
}
