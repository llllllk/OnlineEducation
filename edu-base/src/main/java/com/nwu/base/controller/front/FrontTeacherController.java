package com.nwu.base.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.base.client.CourseClient;
import com.nwu.base.entity.Teacher;
import com.nwu.base.service.TeacherService;
import com.nwu.common.entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/base/front/teachers")
public class FrontTeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping(value = "pageList/{page}/{limit}")
    public CommonResult pageList(@PathVariable("page") Integer page,
                                 @PathVariable("limit")Integer limit){
        Page<Teacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map=teacherService.frontPageList(pageParam);
        return CommonResult.ok().data(map);
    }

    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Teacher teacher = teacherService.getById(id);
        return CommonResult.ok().data("teacher",teacher);
    }
}
