package com.nwu.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.LoginInfoVo;
import com.nwu.course.client.BaseClient;
import com.nwu.course.entity.Comment;
import com.nwu.course.service.CommentService;

import com.nwu.course.vo.CommentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-10-02
 */
@CrossOrigin
@RestController
@RequestMapping("/course/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BaseClient baseClient;

    //分页获取某课程下的所有评论
    @GetMapping(value = "getByCourseId/{courseId}/{page}/{limit}")
    public CommonResult index(@PathVariable("page") Long page, @PathVariable("limit") Long limit, @PathVariable("courseId") String courseId) {
        Page<Comment> pageParam = new Page<>(page, limit);
        Map<String, Object> map=commentService.getCommentsByCourseId(pageParam,courseId);
        return CommonResult.ok().data(map);
    }

    @GetMapping(value = "pageList/{page}/{limit}")
    public CommonResult pageList(@PathVariable("page") Integer page,@PathVariable("limit") Integer limit){
        Page<Comment> pageParam = new Page<>(page, limit);
        IPage<Comment> page1 = commentService.page(pageParam, null);
        return CommonResult.ok().data("total",page1.getTotal()).data("items",page1.getRecords());
    }

    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        commentService.removeById(id);
        return CommonResult.ok();
    }

    //按条件查询提问
    @PostMapping(value = "pageQuery/{courseId}/{page}/{limit}")
    public CommonResult pageQuery(@PathVariable("courseId") Long courseId,
                                  @PathVariable("page") Integer page,
                                  @PathVariable("limit") Integer limit,
                                  @RequestBody(required = false) CommentVo commentVo){
        Page<Comment> pageParam = new Page<>(page, limit);
        QueryWrapper<Comment> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        if (!StringUtils.isBlank(commentVo.getContent())){
            wrapper.like("content",commentVo.getContent());
        }
        IPage<Comment> page1 = commentService.page(pageParam, wrapper);
        return CommonResult.ok().data("total",page1.getTotal()).data("items",page1.getRecords());
    }

    @GetMapping(value = "getTopN/{id}")
    public CommonResult getTopN(@PathVariable("id") Long courseId){
        List<String> topKey = commentService.getTopSixComments(courseId);
        return CommonResult.ok().data("keyList",topKey);
    }
}

