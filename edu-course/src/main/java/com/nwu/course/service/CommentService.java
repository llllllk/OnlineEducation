package com.nwu.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.course.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-10-02
 */
public interface CommentService extends IService<Comment> {

    Map<String, Object> getCommentsByCourseId(Page<Comment> pageParam, String courseId);

    List<String> getTopSixComments(Long courseId);
}
