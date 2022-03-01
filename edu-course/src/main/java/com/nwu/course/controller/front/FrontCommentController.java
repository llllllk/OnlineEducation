package com.nwu.course.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.LoginInfoVo;
import com.nwu.course.client.BaseClient;
import com.nwu.course.entity.Comment;
import com.nwu.course.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/course/front/comments")
@CrossOrigin
public class FrontCommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BaseClient baseClient;

    //学生端新增提问
    @PostMapping(value = "save")
    public CommonResult save(@RequestBody Comment comment, HttpServletRequest request) {
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null) return CommonResult.error().code(1001).message("请登录");
        LoginInfoVo userInfo = baseClient.getLoginInfoById(userId);
        if (userInfo==null) return CommonResult.error().code(1001).message("请登录");
        comment.setUserId(userId);
        comment.setName(userInfo.getName());
        comment.setAvatar(userInfo.getAvatar());
        commentService.save(comment);
        return CommonResult.ok();
    }

    //获取某课程下的提问
    @GetMapping(value = "getByCourseId/{courseId}/{page}/{limit}")
    public CommonResult index(@PathVariable("page") Long page,
                              @PathVariable("limit") Long limit,
                              @PathVariable("courseId") String courseId) {
        Page<Comment> pageParam = new Page<>(page, limit);
        Map<String, Object> map=commentService.getCommentsByCourseId(pageParam,courseId);
        return CommonResult.ok().data(map);
    }

//    //删除提问
//    @DeleteMapping(value = "delete/{id}")
//    public CommonResult delete(@PathVariable("id") Long id){
//        commentService.removeById(id);
//        return CommonResult.ok();
//    }
}
