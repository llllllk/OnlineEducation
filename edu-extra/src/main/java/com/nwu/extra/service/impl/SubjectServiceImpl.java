package com.nwu.extra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.extra.entity.Subject;
import com.nwu.extra.mapper.SubjectMapper;
import com.nwu.extra.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-28
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public boolean deleteByExamId(Long id) {
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("examination_id",id);
        int delete = baseMapper.delete(wrapper);
        return delete>=0;
    }

    @Override
    public List<Subject> getSubListByExamId(Long id) {
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("examination_id",id);
        wrapper.orderByAsc("sort");
        return baseMapper.selectList(wrapper);
    }

}
