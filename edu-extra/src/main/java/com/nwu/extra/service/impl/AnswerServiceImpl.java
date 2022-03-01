package com.nwu.extra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nwu.extra.entity.Answer;
import com.nwu.extra.mapper.AnswerMapper;
import com.nwu.extra.service.AnswerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-03
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

    @Override
    public IPage<Answer> pageQuery(Page<Answer> answerPage,Long questionId) {
        QueryWrapper<Answer> wrapper=new QueryWrapper<>();
        wrapper.eq("question_id",questionId);
        wrapper.orderByAsc("gmt_modified");
        return baseMapper.selectPage(answerPage,wrapper);
    }

    @Override
    public IPage<Answer> getMyAnswer(Page<Answer> questionPage, Long user_id) {
        QueryWrapper<Answer> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",user_id);
        wrapper.orderByAsc("gmt_modified");
        return baseMapper.selectPage(questionPage,wrapper);
    }

    @Override
    public Integer getNumByDate(String date) {
        return baseMapper.getNumByDate(date);
    }
}
