package com.nwu.course.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.course.client.BaseClient;
import com.nwu.course.entity.Course;
import com.nwu.course.service.CourseService;
import com.nwu.course.vo.ChapterVo;
import com.nwu.course.vo.CourseQueryVo;
import com.nwu.course.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/course/courses")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private BaseClient baseClient;

    @GetMapping(value = "getAll")
    public CommonResult getAll(){
        List<Course> list = courseService.list(null);
        return CommonResult.ok().data("courseList",list);
    }

    @PostMapping(value = "pageList/{page}/{limit}")
    public CommonResult pageList(@PathVariable("page") Integer page,
                                 @PathVariable("limit")Integer limit,
                                 @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<Course> pageParam = new Page<>(page, limit);
        IPage<Course> iPage=courseService.pageQuery(pageParam, courseQuery);
        return CommonResult.ok().data("courseList",iPage.getRecords()).data("total",iPage.getTotal());
    }
    //添加课程
    @PostMapping(value = "add")
    public CommonResult add(@RequestBody Course courseInfo){
        Integer deptId = baseClient.getDeptIdByTeacherId(courseInfo.getTeacherId());
        courseInfo.setDeptId(deptId);
        boolean save = courseService.save(courseInfo);
        if (save) return CommonResult.ok().data("courseId",courseInfo.getId());
        else return CommonResult.error().message("添加失败");
    }
    //获取某门课程
    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Course course = courseService.getById(id);
        return CommonResult.ok().data("course",course);
    }
    //更新某门课程信息
    @PutMapping(value = "update")
    public CommonResult update(@RequestBody Course courseInfo){
        boolean b = courseService.updateById(courseInfo);
        if (b) return CommonResult.ok().data("courseId",courseInfo.getId());
        else return CommonResult.error().message("修改失败，请重试");
    }
    //删除课程
    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        boolean flag=courseService.deleteCourseById(id);
        if (flag) return CommonResult.ok();
        else return CommonResult.error().message("删除失败，请重试");
    }
    //获取课程下的所有章节
    @GetMapping(value = "getAllChapters/{id}")
    public CommonResult getAllChapters(@PathVariable("id") Long id){
        List<ChapterVo> res=courseService.getAllChapters(id);
        return CommonResult.ok().data("chapters",res);
    }

    @GetMapping(value = "getCourseConcreteInfo/{id}")
    public CommonResult getCourseConcreteInfo(@PathVariable("id") Long id){
        CourseVo vo=courseService.getCourseConcreteInfo(id);
        return CommonResult.ok().data("courseInfo",vo);
    }
    //发布课程
    @PutMapping(value = "publish/{id}")
    public CommonResult publishCourse(@PathVariable("id") Long id){
        Course byId = courseService.getById(id);
        byId.setStatus(1);
        boolean flag = courseService.updateById(byId);
        if (flag) return CommonResult.ok();
        else return CommonResult.error().message("课程发布失败");
    }
}

