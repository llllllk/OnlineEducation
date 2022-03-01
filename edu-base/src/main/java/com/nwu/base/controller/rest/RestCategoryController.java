package com.nwu.base.controller.rest;


import com.nwu.base.entity.CourseCategory;
import com.nwu.base.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/base/rest/categories")
public class RestCategoryController {

    @Autowired
    private CourseCategoryService categoryService;

    //获取院系名称
    @GetMapping(value = "getName/{id}")
    public String getCategoryName(@PathVariable("id") Integer id){
        CourseCategory category = categoryService.getById(id);
        return category.getName();
    }
}
