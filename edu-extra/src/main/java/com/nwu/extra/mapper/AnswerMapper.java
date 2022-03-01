package com.nwu.extra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nwu.extra.entity.Answer;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-11-03
 */
public interface AnswerMapper extends BaseMapper<Answer> {
    Integer getNumByDate(String myDate);
}
