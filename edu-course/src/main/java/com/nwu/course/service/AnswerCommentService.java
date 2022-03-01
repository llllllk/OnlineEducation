package com.nwu.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.course.entity.AnswerComment;
import com.nwu.course.entity.Comment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
public interface AnswerCommentService extends IService<AnswerComment> {

    AnswerComment getByCommentId(Long commentId);
}
