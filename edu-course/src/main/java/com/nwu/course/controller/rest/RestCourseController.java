package com.nwu.course.controller.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.course.client.BaseClient;
import com.nwu.course.entity.Course;
import com.nwu.course.entity.Record;
import com.nwu.course.service.CourseService;
import com.nwu.course.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/course/rest/courses")
@CrossOrigin
public class RestCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private RecordService recordService;

    //获取某天的课程数
    @GetMapping(value = "getNumByDate/{date}")
    public Integer getCourseNumByDate(@PathVariable("date") String date){
        return courseService.getNumByDate(date);
    }


    //根据课程ID集合获取课程
    @GetMapping(value = "getCourseListByIds/{uList}/{iList}")
    public CommonResult getCourseListByIds(@PathVariable(value = "uList") String uList,
                                           @PathVariable(value = "iList") String iList){
        List<Course> userCourses = courseService.getCoursesByIds(uList);
        List<Course> itemCourses = courseService.getCoursesByIds(iList);
        return CommonResult.ok().data("userCourses",userCourses).data("itemCourses",itemCourses);
    }

    //获取k个用户选过的课程ID集合
    @GetMapping(value = "getCourseIdsByUids/{uList}")
    public CommonResult getCourseIdsByUids(@PathVariable(value = "uList") String uList){
        String[] split = uList.split(":");
        QueryWrapper<Record> wrapper=new QueryWrapper<>();
        wrapper.in("user_id", Arrays.asList(split));
        List<Record> list = recordService.list(wrapper);
        List<Long> ids = new ArrayList<>();
        HashSet<Long> set = new HashSet<>();

        for (Record record : list) {
            if (!set.contains(record.getCourseId())){
                set.add(record.getCourseId());
                ids.add(record.getCourseId());
            }
        }
        return CommonResult.ok().data("ids",ids);
    }

}
