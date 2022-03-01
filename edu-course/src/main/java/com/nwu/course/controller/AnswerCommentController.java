package com.nwu.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.LoginInfoVo;

import com.nwu.course.client.BaseClient;
import com.nwu.course.entity.AnswerComment;
import com.nwu.course.entity.Comment;
import com.nwu.course.service.AnswerCommentService;
import com.nwu.course.service.CommentService;
import com.nwu.course.vo.CommentAnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-11-06
 */
@RestController
@RequestMapping("/course/answerComments")
@CrossOrigin
public class AnswerCommentController {

    @Autowired
    private AnswerCommentService answerCommentService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private CommentService commentService;


    //查看某个问题的回复
    @GetMapping(value = "getAnswerByCommentId/{commentId}")
    public CommonResult getAnswerByCommentId(@PathVariable("commentId") Long commentId){
        QueryWrapper<AnswerComment> wrapper=new QueryWrapper<>();
        wrapper.eq("comment_id",commentId);
        AnswerComment answer = answerCommentService.getOne(wrapper);
        return CommonResult.ok().data("answer",answer);
    }

    //老师回复问题
    @PostMapping(value = "add")
    public CommonResult addAnswer(@RequestBody(required = false) CommentAnswerVo commentVo,
                                  HttpServletRequest request){
        AnswerComment answerComment = answerCommentService.getByCommentId(commentVo.getCommentId());
        if (answerComment!=null){  //修改
            answerComment.setAnswerContent(commentVo.getContent());
            answerCommentService.updateById(answerComment);
        }else { //新增
            Long userId = JwtUtil.getMemberIdByJwtToken(request);
            LoginInfoVo loginInfo = baseClient.getLoginInfoById(userId);
            Comment comment = commentService.getById(commentVo.getCommentId());
            answerComment=new AnswerComment();
            answerComment.setCommentId(commentVo.getCommentId());
            answerComment.setCourseId(comment.getCourseId());
            answerComment.setName(comment.getName());
            answerComment.setContent(comment.getContent());
            answerComment.setAvatar(comment.getAvatar());
            answerComment.setAnswerContent(commentVo.getContent());
            answerComment.setAnswerName(loginInfo.getName());
            answerComment.setAnswerAvatar(loginInfo.getAvatar());
            answerCommentService.save(answerComment);
            comment.setStatus(1);
            commentService.updateById(comment);
        }

        return CommonResult.ok();
    }


}

