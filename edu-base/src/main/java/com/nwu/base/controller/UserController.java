package com.nwu.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.base.entity.User;
import com.nwu.base.service.RoleService;
import com.nwu.base.service.UserService;
import com.nwu.base.vo.QueryVo;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-10-17
 */
@CrossOrigin
@RestController
@RequestMapping("/base/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //查看所有管理用户
    @PostMapping(value = "getPage/{page}/{limit}")
    public CommonResult index(@PathVariable Long page,
                              @PathVariable Long limit,
                              @RequestBody(required = false) QueryVo userQuery) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQuery.getName())) {
            wrapper.like("name",userQuery.getName());
        }
        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return CommonResult.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    //新增用户
    @PostMapping(value = "save")
    public CommonResult save(@RequestBody User user) {
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        userService.save(user);
        return CommonResult.ok().message("添加成功");
    }

    //删除管理用户
    @DeleteMapping(value ="remove/{id}")
    public CommonResult remove(@PathVariable Long id) {
        userService.removeById(id);
        return CommonResult.ok();
    }

    //修改管理用户
    @PutMapping(value ="update")
    public CommonResult updateById(@RequestBody User user) {
        userService.updateById(user);
        return CommonResult.ok().message("修改信息成功");
    }

    //获取某个用户信息
    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return CommonResult.ok().data("user",user);
    }

    //根据id列表删除管理用户
    @DeleteMapping(value ="batchRemove")
    public CommonResult batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return CommonResult.ok();
    }

    //获取用户角色和所有角色
    @GetMapping(value ="getRolesByUserId/{userId}")
    public CommonResult getRolesByUserId(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return CommonResult.ok().data(roleMap);
    }

    //根据用户分配角色
    @PostMapping(value ="addRole")
    public CommonResult addRole(@RequestParam Long userId,@RequestParam Long[] roleIds) {
        roleService.saveUserRole(userId,roleIds);
        return CommonResult.ok();
    }
}

