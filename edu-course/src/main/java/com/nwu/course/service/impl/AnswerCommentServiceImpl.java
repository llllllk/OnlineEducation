package com.nwu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.course.entity.AnswerComment;
import com.nwu.course.entity.Comment;
import com.nwu.course.mapper.AnswerCommentMapper;
import com.nwu.course.service.AnswerCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@Service
public class AnswerCommentServiceImpl extends ServiceImpl<AnswerCommentMapper, AnswerComment> implements AnswerCommentService {

    @Override
    public AnswerComment getByCommentId(Long commentId) {
        QueryWrapper<AnswerComment> wrapper=new QueryWrapper<>();
        wrapper.eq("comment_id",commentId);
        return baseMapper.selectOne(wrapper);
    }
}
