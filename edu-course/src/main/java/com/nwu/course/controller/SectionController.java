package com.nwu.course.controller;


import com.nwu.common.entity.CommonResult;
import com.nwu.course.entity.Section;
import com.nwu.course.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/course/sections")
@CrossOrigin
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @PostMapping(value = "add")
    public CommonResult add(@RequestBody Section section){
        boolean exist=sectionService.sectionExist(section);
        if (exist) return CommonResult.error().message("该序号已存在");
        boolean save=sectionService.addSection(section);
        if (save) return CommonResult.ok();
        else return CommonResult.error();
    }

    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Section byId = sectionService.getById(id);
        return CommonResult.ok().data("section",byId);
    }

    @PutMapping(value = "update")
    public CommonResult update(@RequestBody Section section){
        boolean flag = sectionService.updateById(section);
        if (flag) return CommonResult.ok();
        else return CommonResult.error();
    }

    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        boolean flag=sectionService.removeSection(id);
        return flag?CommonResult.ok():CommonResult.error();
    }
}

