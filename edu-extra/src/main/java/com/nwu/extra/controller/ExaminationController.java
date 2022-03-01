package com.nwu.extra.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.extra.entity.Examination;
import com.nwu.extra.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/extra/examinations")
@CrossOrigin
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;


    @PostMapping(value = "add")
    public CommonResult add(@RequestBody(required = false) Examination newExamination){
        examinationService.save(newExamination);
        return CommonResult.ok();
    }

    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        boolean flag = examinationService.deleteExam(id);
        if (flag) return CommonResult.ok();
        else return CommonResult.error();
    }

    @PutMapping(value = "update")
    public CommonResult update(@RequestBody(required = false) Examination examination){
        examinationService.updateById(examination);
        return CommonResult.ok();
    }

    @GetMapping(value = "getByCid/{id}")
    public CommonResult getByCourseId(@PathVariable("id") Long id){
        QueryWrapper<Examination> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        List<Examination> list = examinationService.list(wrapper);
        return CommonResult.ok().data("examinations",list);
    }

    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Examination byId = examinationService.getById(id);
        return CommonResult.ok().data("examination",byId);
    }

    @GetMapping(value = "changeStatus/{id}/{value}")
    public CommonResult changeStatus(@PathVariable("id") Long id,
                                     @PathVariable("value") Integer value){
        Examination examination = examinationService.getById(id);
        examination.setStatus(value);
        examinationService.updateById(examination);
        return CommonResult.ok();
    }
}
