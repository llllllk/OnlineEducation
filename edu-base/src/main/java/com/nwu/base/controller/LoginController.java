package com.nwu.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.base.entity.User;
import com.nwu.base.service.UserService;
import com.nwu.base.vo.LoginVo;
import com.nwu.base.vo.RegisterVo;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.exception.MyException;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.utils.MD5Util;
import com.nwu.common.vo.LoginInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/base/cas")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "login")
    public CommonResult login(@RequestBody(required = false) LoginVo vo){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("sid",vo.getSid());
        User one = userService.getOne(wrapper);
        if (one==null) return CommonResult.error().message("账号不存在");
        if (one.getPassword().equals(MD5Util.encrypt(vo.getPassword()))){
            String token = JwtUtil.getJwtToken(one.getId(), one.getName());
            return CommonResult.ok().data("token",token);
        }else {
            return CommonResult.error().message("密码错误");
        }
    }

    @GetMapping(value = "info")
    public CommonResult info(HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);

        User user = userService.getById(userId);
        LoginInfoVo loginInfoVo=new LoginInfoVo();
        BeanUtils.copyProperties(user,loginInfoVo);
        return CommonResult.ok()
                .data("roles","[admin]")
                .data("avatar",user.getAvatar())
                .data("name",user.getName())
                .data("item",loginInfoVo);

    }

    @GetMapping(value = "logout")
    public CommonResult logout(HttpServletRequest request){
        return CommonResult.ok();
    }

    @PostMapping(value = "register")
    public CommonResult register(@RequestBody RegisterVo registerVo){
        userService.register(registerVo);
        return CommonResult.ok();
    }

}
