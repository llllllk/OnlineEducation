package com.nwu.extra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.extra.entity.Question;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-11-03
 */
public interface QuestionService extends IService<Question> {

    boolean updateAnswerNum(Long questionId);

    Integer getNumByDate(String date);
}
