package com.nwu.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.base.entity.Teacher;
import com.nwu.base.vo.TeacherVo;

import java.util.Map;

public interface TeacherService extends IService<Teacher> {

    IPage<Teacher> pageListByCondition(Page<Teacher> page, TeacherVo teacherVo);

    Map<String, Object> frontPageList(Page<Teacher> pageParam);
}
