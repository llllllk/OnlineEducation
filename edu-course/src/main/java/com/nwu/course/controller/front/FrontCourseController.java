package com.nwu.course.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.vo.FrontCourseVo;
import com.nwu.course.entity.Course;
import com.nwu.course.entity.Section;
import com.nwu.course.service.CourseService;
import com.nwu.course.service.SectionService;
import com.nwu.course.vo.ChapterVo;
import com.nwu.course.vo.FrontCourseQueryVo;
import com.nwu.course.vo.FrontOneCourseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course/front/courses")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SectionService sectionService;

    @PostMapping(value = "pageList/{page}/{limit}")
    public CommonResult pageList(@PathVariable("page") Integer page,
                                 @PathVariable("limit")Integer limit,
                                 @RequestBody(required = false) FrontCourseQueryVo courseQuery){
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map=courseService.frontPageQuery(pageParam, courseQuery);
        return CommonResult.ok().data(map);
    }

    //查看课程详情
    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        FrontOneCourseVo courseVo=courseService.frontGetCourseInfo(id);
        courseService.updateViewNum(id);
        List<ChapterVo> res=courseService.getAllChapters(id);
        return CommonResult.ok().data("courseInfo",courseVo).data("chapters",res);
    }

    //查看xx老师的所有课程
    @GetMapping(value = "getByTid/{teacherId}")
    public CommonResult getByTid(@PathVariable("teacherId") Long teacherId){
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> list = courseService.list(wrapper);
        return CommonResult.ok().data("courseList",list);
    }

    //获取我的课程
    @GetMapping(value = "frontGetMyCourse")
    public List<FrontCourseVo> getCourse(@RequestParam("ids") List<Long> ids){
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.in("id",ids);
        List<Course> list = courseService.list(wrapper);
        List<FrontCourseVo> res=new ArrayList<>();
        for (Course course : list) {
            FrontCourseVo fcv=new FrontCourseVo();
            BeanUtils.copyProperties(course,fcv);
            res.add(fcv);
        }
        return res;
    }
    @GetMapping(value = "getCourseIdBySecId/{sectionId}")
    public CommonResult getCourseIdBySecId(@PathVariable("sectionId") String sectionId){
        QueryWrapper<Section> wrapper=new QueryWrapper<>();
        wrapper.eq("video_id",sectionId);
        Section section = sectionService.getOne(wrapper);
        System.out.println(section.getCourseId());
        return CommonResult.ok().data("id",section.getCourseId());
    }
}
