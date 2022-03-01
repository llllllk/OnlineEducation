package com.nwu.extra.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.extra.entity.Answer;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-11-03
 */
public interface AnswerService extends IService<Answer> {

    IPage<Answer> pageQuery(Page<Answer> answerPage,Long questionId);

    IPage<Answer> getMyAnswer(Page<Answer> questionPage, Long user_id);

    Integer getNumByDate(String date);
}
