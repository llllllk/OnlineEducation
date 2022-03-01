package com.nwu.course.controller.front;


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

@RestController
@RequestMapping("/course/front/answerComments")
@CrossOrigin
public class FrontAnswerCommentController {
    @Autowired
    private AnswerCommentService answerCommentService;

    @GetMapping(value = "getAnswerByCourseId/{courseId}")
    public CommonResult getCommentAnswerByCourseId(@PathVariable("courseId") Long courseId){
        QueryWrapper<AnswerComment> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<AnswerComment> list = answerCommentService.list(wrapper);
        return CommonResult.ok().data("lists",list);
    }
}
