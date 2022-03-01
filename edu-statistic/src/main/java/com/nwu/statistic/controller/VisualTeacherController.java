package com.nwu.statistic.controller;


import com.nwu.common.entity.CommonResult;
import com.nwu.statistic.service.VisualTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-12-11
 */
@RestController
@RequestMapping("/statistic/teacher")
@CrossOrigin
public class VisualTeacherController {

    @Autowired
    private VisualTeacherService teacherService;

    @GetMapping(value = "showFigure/{courseId}/{day}")
    public CommonResult showFigure(@PathVariable String courseId,@PathVariable String day) {
        Map<String, Object> map = teacherService.showFigure(courseId,day);
        return CommonResult.ok().data(map);
    }

    @PostMapping(value = "showTrend")
    public CommonResult showFigure(@RequestBody Map<String,String> maps) {
        Map<String, Object> map = teacherService.showTrend(
                maps.get("courseId"),maps.get("type"),
                maps.get("begin"),maps.get("end"));
        return CommonResult.ok().data(map);
    }
}

