package com.nwu.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.base.entity.Role;
import com.nwu.base.entity.UserRole;
import com.nwu.base.mapper.RoleMapper;
import com.nwu.base.service.RoleService;
import com.nwu.base.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-10-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        List<Role> allRolesList =baseMapper.selectList(null);
        //根据用户id，查询用户拥有的角色id
        QueryWrapper<UserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserRole> assignRoles = userRoleService.list(queryWrapper);
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("myRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    @Override
    public void saveUserRole(Long userId, Long[] roleIds) {
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        List<UserRole> userRoleList = new ArrayList<>();
        for(Long roleId : roleIds) {
            if(roleId==null) continue;
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }
}
