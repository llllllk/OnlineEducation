package com.nwu.base.controller.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.base.entity.User;
import com.nwu.base.service.UserService;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.exception.MyException;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.LoginInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/base/rest/users")
public class RestUserController {

    @Autowired
    private UserService userService;

    //获取所有用户的院系信息
    @GetMapping(value = "getUserDepartment")
    public CommonResult getUserDepartment(){
        List<User> list = userService.list(null);
        Map<Long,Integer> map=new HashMap<>();
        for (User user : list) {
            map.put(user.getId(),user.getDeptId());
        }
        return CommonResult.ok().data("deptInfo",map);
    }

    //获取用户登录信息
    @GetMapping(value = "getLoginInfo")
    public CommonResult getLoginInfo(HttpServletRequest request){
        try {
            Long userId = JwtUtil.getMemberIdByJwtToken(request);
            LoginInfoVo loginInfoVo = userService.getLoginInfo(userId);
            return CommonResult.ok().data("item", loginInfoVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(444,"error");
        }
    }

    //根据用户ID获取用户登录信息
    @GetMapping(value = "getLoginInfoById/{id}")
    public LoginInfoVo getLoginInfoById(@PathVariable("id") Long id){
        try {
            User user=userService.getById(id);
            LoginInfoVo loginInfoVo=new LoginInfoVo();
            BeanUtils.copyProperties(user,loginInfoVo);
            return loginInfoVo;
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(444,"error");
        }
    }

    //获取名字为xx的所有用户集合
    @GetMapping(value = "getUidListByName/{name}")
    public List<Long> getUidListByName(@PathVariable("name") String name){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("name",name);
        List<User> list = userService.list(wrapper);
        List<Long> users = list.stream().map(User::getId).collect(Collectors.toList());
        return users;
    }

    //根据用户ID获取用户姓名
    @GetMapping(value = "getUserName/{id}")
    public String getUserName(@PathVariable("id")Long id){
        User user = userService.getById(id);
        return user.getName();
    }
}
