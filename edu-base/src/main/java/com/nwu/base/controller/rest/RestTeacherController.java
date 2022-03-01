package com.nwu.base.controller.rest;

import com.nwu.base.entity.Teacher;
import com.nwu.base.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/base/rest/teachers")
public class RestTeacherController {
    @Autowired
    private TeacherService teacherService;

    //获取教师的院系ID
    @GetMapping(value = "getDeptIdByTeacherId/{id}")
    public Integer getDeptIdByTeacherId(@PathVariable("id")Integer teacherId){
        Teacher teacher = teacherService.getById(teacherId);
        return teacher.getDeptId();
    }

}
