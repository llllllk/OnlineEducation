package com.nwu.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.base.entity.Teacher;
import com.nwu.base.mapper.TeacherMapper;

import com.nwu.base.service.DepartmentService;
import com.nwu.base.service.TeacherService;
import com.nwu.base.vo.TeacherVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-07-02
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public IPage<Teacher> pageListByCondition(Page<Teacher> page, TeacherVo teacherVo) {
        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherVo.getName())){
            wrapper.eq("name",teacherVo.getName());
        }
        if (!StringUtils.isEmpty(teacherVo.getLevel())){
            wrapper.eq("level",teacherVo.getLevel());
        }
        if (!StringUtils.isEmpty(teacherVo.getDepartment())){
            wrapper.eq("dept_id",teacherVo.getDepartment());
        }
        wrapper.orderByDesc("gmt_create");
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public Map<String, Object> frontPageList(Page<Teacher> pageParam) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("gmt_create");
        baseMapper.selectPage(pageParam,queryWrapper);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", pageParam.getRecords());
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return map;
    }

}
