package com.nwu.extra.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.extra.entity.Question;
import com.nwu.extra.mapper.QuestionMapper;
import com.nwu.extra.service.QuestionService;
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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public boolean updateAnswerNum(Long questionId) {
        Question question = baseMapper.selectById(questionId);
        question.setAnswerNum(question.getAnswerNum()+1);
        baseMapper.updateById(question);
        return true;
    }

    @Override
    public Integer getNumByDate(String date) {
        return baseMapper.getNumByDate(date);
    }
}
