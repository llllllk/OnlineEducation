package com.nwu.base.controller;


import com.nwu.base.service.DepartmentService;
import com.nwu.common.entity.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/base/departments")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value = "getAll")
    public CommonResult getAll(){
        return CommonResult.ok().data("departments",departmentService.list(null));
    }

}

