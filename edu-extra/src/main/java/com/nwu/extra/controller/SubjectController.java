package com.nwu.extra.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.extra.entity.Subject;
import com.nwu.extra.service.SubjectService;
import com.nwu.extra.vo.SubjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 题目表 前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/extra/subjects")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping(value = "add")
    public CommonResult add(@RequestBody Subject subject){
        subject.setSelectA("无");
        subject.setSelectB("无");
        subject.setSelectC("无");
        subject.setSelectD("无");
        subjectService.save(subject);
        return CommonResult.ok();
    }

    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable Long id){
        subjectService.removeById(id);
        return CommonResult.ok();
    }

    @PutMapping(value = "update")
    public CommonResult update(@RequestBody Subject subject){
        subjectService.updateById(subject);
        return CommonResult.ok();
    }

    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Subject subject = subjectService.getById(id);
        return CommonResult.ok().data("subject",subject);
    }

    @GetMapping(value = "getByExamId/{id}")
    public CommonResult getByExamId(@PathVariable("id") Long id){
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("examination_id",id);
        wrapper.orderByAsc("sort");
        List<Subject> list = subjectService.list(wrapper);
        return CommonResult.ok().data("subjectList",list);
    }

    @PutMapping(value = "updateOptionAndAnswer")
    public CommonResult updateOptionAndAnswer(@RequestBody SubjectDto newOption){
        Subject subject = subjectService.getById(newOption.getId());
        String type=newOption.getType();
        switch (type) {
            case "a":
                subject.setSelectA(newOption.getContent());
                break;
            case "b":
                subject.setSelectB(newOption.getContent());
                break;
            case "c":
                subject.setSelectC(newOption.getContent());
                break;
            case "d":
                subject.setSelectD(newOption.getContent());
                break;
            case "e":
                subject.setRightAnswer(newOption.getContent());
                break;
        }
        subjectService.updateById(subject);
        return CommonResult.ok();
    }

}

