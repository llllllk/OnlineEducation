package com.nwu.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.base.entity.CourseCategory;
import com.nwu.base.service.CourseCategoryService;
import com.nwu.common.entity.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-08-12
 */
@RestController
@RequestMapping("/base/categories")
@CrossOrigin
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService categoryService;

    //获取所有类别
    @GetMapping(value = "getAll")
    public CommonResult getAll(){
        List<CourseCategory> list = categoryService.list(null);
        return CommonResult.ok().data("categories",list);
    }

    //分页获取类别
    @GetMapping(value = "pageList/{current}/{limit}")
    public CommonResult pageList(@PathVariable(value = "current") Integer current,
                                 @PathVariable(value = "limit") Integer limit){
        Page<CourseCategory> page=new Page<>(current,limit);
        IPage<CourseCategory> result = categoryService.page(page, null);
        return CommonResult.ok().data("records",result.getRecords()).data("total",result.getTotal());
    }

    //添加类别
    @PostMapping(value = "add")
    public CommonResult add(@RequestBody CourseCategory category){
        boolean save = categoryService.save(category);
        return save?CommonResult.ok():CommonResult.error().message("添加失败");
    }

    //删除指定类别
    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable(value = "id") Integer id){
        boolean flag = categoryService.removeById(id);
        if (flag) return CommonResult.ok();
        else return CommonResult.error().message("删除失败，请重试");
    }
}

