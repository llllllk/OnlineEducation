package com.nwu.base.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/base/permissions")
public class PermissionController {

//    @Autowired
//    private PermissionService permissionService;
//
//    //获取全部菜单
//    @ApiOperation(value = "查询所有菜单")
//    @GetMapping
//    public CommonResult indexAllPermission() {
//        List<Permission> list =  permissionService.queryAllMenu();
//        return CommonResult.ok().data("children",list);
//    }
//
//    @ApiOperation(value = "递归删除菜单")
//    @DeleteMapping("remove/{id}")
//    public CommonResult remove(@PathVariable Long id) {
//        permissionService.removeRecursive(id);
//        return CommonResult.ok();
//    }
//
//    @ApiOperation(value = "给角色分配权限")
//    @PostMapping("/doAssign")
//    public CommonResult doAssign(Long roleId,Long[] permissionId) {
//        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
//        return CommonResult.ok();
//    }
//
//    @ApiOperation(value = "根据角色获取菜单")
//    @GetMapping("toAssign/{roleId}")
//    public CommonResult toAssign(@PathVariable Long roleId) {
//        List<Permission> list = permissionService.selectAllMenu(roleId);
//        return CommonResult.ok().data("children", list);
//    }
//
//    @ApiOperation(value = "新增菜单")
//    @PostMapping("save")
//    public CommonResult save(@RequestBody Permission permission) {
//        permissionService.save(permission);
//        return CommonResult.ok();
//    }
//
//    @ApiOperation(value = "修改菜单")
//    @PutMapping("update")
//    public CommonResult updateById(@RequestBody Permission permission) {
//        permissionService.updateById(permission);
//        return CommonResult.ok();
//    }

}

