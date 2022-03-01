package com.nwu.course.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.FrontCourseVo;
import com.nwu.course.entity.CourseCollect;
import com.nwu.course.service.CourseCollectService;
import com.nwu.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course/front/collects")
@CrossOrigin
public class FrontCollectController {

    @Autowired
    private CourseCollectService courseCollectService;

    @Autowired
    private CourseService courseService;

    //添加到我的收藏
    @GetMapping(value = "addCourseCollect/{courseId}")
    public CommonResult insertCourseCollect(@PathVariable("courseId") Long courseId,
                                            HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null) return CommonResult.error().code(1001).message("请登录");
        CourseCollect courseCollect=new CourseCollect();
        courseCollect.setCourseId(courseId);
        courseCollect.setUserId(userId);
        courseCollectService.save(courseCollect);
        return CommonResult.ok().message("添加收藏成功");
    }


    //获取本人是否已收藏
    @GetMapping(value = "getCollectStatus/{courseId}")
    public CommonResult getCollectStatus(@PathVariable("courseId") Long courseId,
                                         HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null) return CommonResult.error().code(1001).message("请登录");
        QueryWrapper<CourseCollect> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("course_id",courseId);
        int count = courseCollectService.count(wrapper);
        return CommonResult.ok().data("isCollect",count>0);
    }

    //查看我的收藏
    @GetMapping(value = "myCollect")
    public CommonResult getMyCollect(HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null) {
            return CommonResult.error().code(1001).message("请先登录！");
        }else {
            QueryWrapper<CourseCollect> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            List<CourseCollect> list = courseCollectService.list(wrapper);
            List<Long> ids=new ArrayList<>();
            for (CourseCollect courseCollect : list) {
                ids.add(courseCollect.getCourseId());
            }
            List<FrontCourseVo> courseVoList = courseService.getCourseVoByIds(ids);
            return CommonResult.ok().data("collectList", courseVoList);
        }
    }

    //删除收藏
    @DeleteMapping(value = "deleteCollect/{courseId}")
    public CommonResult deleteCollect(@PathVariable("courseId") Long courseId,
                                      HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null){
            return CommonResult.error().code(1001).message("请先登录！");
        }else{
            QueryWrapper<CourseCollect> wrapper=new QueryWrapper<>();
            wrapper.eq("course_id",courseId);
            wrapper.eq("user_id",userId);
            courseCollectService.remove(wrapper);
            return CommonResult.ok();
        }
    }

}
