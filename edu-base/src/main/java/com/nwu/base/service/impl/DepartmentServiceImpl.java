package com.nwu.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nwu.base.entity.Department;
import com.nwu.base.mapper.DepartmentMapper;
import com.nwu.base.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-07-02
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
