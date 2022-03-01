package com.nwu.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.base.entity.Role;


import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-10-17
 */
public interface RoleService extends IService<Role> {
    //获取用户角色和所有角色
    Map<String, Object> findRoleByUserId(String userId);
    //根据用户分配角色
    void saveUserRole(Long userId, Long[] roleIds);
}
