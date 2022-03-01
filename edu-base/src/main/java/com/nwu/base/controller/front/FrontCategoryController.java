package com.nwu.base.controller.front;

import com.nwu.base.entity.CourseCategory;
import com.nwu.base.service.CourseCategoryService;
import com.nwu.common.entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/base/front/categories")
@CrossOrigin
public class FrontCategoryController {
    @Autowired
    private CourseCategoryService categoryService;

    //获取所有类别
    @GetMapping(value = "getAll")
    public CommonResult getAll(){
        List<CourseCategory> list = categoryService.list(null);
        return CommonResult.ok().data("categories",list);
    }
}
