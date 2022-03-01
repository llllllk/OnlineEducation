package com.nwu.course.controller;


import com.nwu.common.entity.CommonResult;
import com.nwu.course.entity.Chapter;
import com.nwu.course.service.ChapterService;
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
@RequestMapping("/course/chapters")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Chapter chapter = chapterService.getById(id);
        return CommonResult.ok().data("chapter",chapter);
    }
    @PostMapping(value = "add")
    public CommonResult add(@RequestBody Chapter chapter){
        boolean exist=chapterService.sortExist(chapter);
        if (exist) return CommonResult.error().message("章节序号已存在");
        boolean save = chapterService.save(chapter);
        if (save) return CommonResult.ok();
        else return CommonResult.error();
    }
    @PutMapping(value = "update")
    public CommonResult update(@RequestBody Chapter chapter){
        boolean save = chapterService.updateById(chapter);
        if (save) return CommonResult.ok();
        else return CommonResult.error();
    }
    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        boolean b = chapterService.removeChapterById(id);
        if (b) return CommonResult.ok();
        else return CommonResult.error();
    }
}

