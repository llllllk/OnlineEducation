package com.nwu.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.base.entity.Department;
import com.nwu.base.entity.Teacher;
import com.nwu.base.service.DepartmentService;
import com.nwu.base.service.TeacherService;
import com.nwu.base.vo.TeacherVo;
import com.nwu.common.entity.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/base/teachers")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private DepartmentService departmentService;

    //添加老师
    @PostMapping(value = "add")
    public CommonResult add(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        return save?CommonResult.ok():CommonResult.error().message("添加失败");
    }

    //查询所有老师
    @GetMapping(value = "findAll")
    public CommonResult findAll(){
        List<Teacher> list = teacherService.list(null);
        return CommonResult.ok().data("teachers",list);
    }

    //删除老师
    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable(value = "id") Integer id){
        boolean flag = teacherService.removeById(id);
        if (flag) return CommonResult.ok();
        else return CommonResult.error().message("删除失败，请重试");
    }

    //更新老师信息
    @PutMapping(value = "update")
    public CommonResult update(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if (b) return CommonResult.ok();
        else return CommonResult.error().message("更新失败,请重试");
    }

    //获取某个老师的信息
    @GetMapping(value = "getTeacher/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Teacher teacher = teacherService.getById(id);
        return CommonResult.ok().data("teacher",teacher);
    }

    //分页查找老师
    @GetMapping(value = "pageList/{current}/{limit}")
    public CommonResult pageList(@PathVariable("current") Integer current,
                                 @PathVariable("limit") Integer limit){
        Page<Teacher> page=new Page<>(current,limit);
        IPage<Teacher> teacherIPage = teacherService.page(page, null);
        return CommonResult.ok().data("records",exChange(teacherIPage)).data("total",teacherIPage.getTotal());
    }
    //分页按条件查找老师
    @PostMapping(value = "pageListByCondition/{current}/{limit}")
    public CommonResult pageListByCondition(@PathVariable("current") Integer current,
                                            @PathVariable("limit") Integer limit,
                                            @RequestBody(required = false) TeacherVo teacherVo){
        Page<Teacher> page=new Page<>(current,limit);
        IPage<Teacher> teacherIPage=teacherService.pageListByCondition(page,teacherVo);
        return CommonResult.ok().data("records",exChange(teacherIPage)).data("total",teacherIPage.getTotal());
    }

    //将老师列表转化为前端可视的结果
    public List<TeacherVo> exChange(IPage<Teacher> teacherIPage){
        List<Teacher> records = teacherIPage.getRecords();
        Map<Integer,String> deptMap=new HashMap<>();
        List<Department> list = departmentService.list(null);
        for (Department department : list) {
            deptMap.put(department.getId(),department.getName());
        }
        List<TeacherVo> result=new ArrayList<>();
        for (Teacher record : records) {
            TeacherVo vo=new TeacherVo();
            BeanUtils.copyProperties(record,vo);
            vo.setDepartment(deptMap.get(record.getDeptId()));
            result.add(vo);
        }
        return result;
    }
}

