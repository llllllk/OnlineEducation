package com.nwu.base.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.base.entity.Role;
import com.nwu.base.service.RoleService;
import com.nwu.base.vo.QueryVo;
import com.nwu.common.entity.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/base/roles")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    //查看所有角色
    @PostMapping(value = "getPage/{page}/{limit}")
    public CommonResult index(@PathVariable Long page,
                              @PathVariable Long limit,
                              @RequestBody(required = false) QueryVo roleQuery) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(roleQuery.getName())) {
            wrapper.like("name",roleQuery.getName());
        }
        IPage<Role> pageModel = roleService.page(pageParam, wrapper);
        return CommonResult.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    //新增角色
    @PostMapping(value = "save")
    public CommonResult save(@RequestBody Role role) {
        roleService.save(role);
        return CommonResult.ok().message("添加成功");
    }

    //修改角色
    @PutMapping(value ="update")
    public CommonResult updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return CommonResult.ok().message("修改信息成功");
    }

    //删除角色
    @DeleteMapping(value ="remove/{id}")
    public CommonResult remove(@PathVariable Long id) {
        roleService.removeById(id);
        return CommonResult.ok();
    }

    //获取角色信息
    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return CommonResult.ok().data("role",role);
    }

}

