package com.nwu.extra.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.extra.entity.Examination;
import com.nwu.extra.entity.Subject;
import com.nwu.extra.service.ExaminationService;
import com.nwu.extra.service.SubjectService;
import com.nwu.extra.vo.GradeVo;
import com.nwu.extra.vo.MyGradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/extra/front/examinations")
@CrossOrigin
public class FrontExaminationController {

    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private SubjectService subjectService;

    //获取某课程的考试卷和考试成绩
    @GetMapping(value = "getByCid/{id}")
    public CommonResult getByCourseId(@PathVariable("id") Long id,HttpServletRequest request){
        QueryWrapper<Examination> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.eq("status",1);
        List<Examination> list = examinationService.list(wrapper);
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        List<MyGradeVo> myGradeList = examinationService.getMyGrade(id,userId);
        System.out.println(myGradeList);
        return CommonResult.ok().data("examinations",list).data("myGradeList",myGradeList);
    }

    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Examination byId = examinationService.getById(id);
        return CommonResult.ok().data("examination",byId);
    }

    //获取某试卷的所有题目
    @GetMapping(value = "getSubjects/{id}")
    public CommonResult getSubjects(@PathVariable("id") Long id){
        List<Subject> subjectList = subjectService.getSubListByExamId(id);
        return CommonResult.ok().data("subjectList",subjectList);
    }

    //提交考试并计算考试成绩
    @PostMapping(value = "addGradeRecord")
    public CommonResult addGradeRecord(@RequestBody GradeVo gradeVo,
                                       HttpServletRequest request){
        Long user_id = JwtUtil.getMemberIdByJwtToken(request);
        double total=examinationService.getTotal(gradeVo,user_id);
        return CommonResult.ok().data("total",total);
    }

    //获取我的考试成绩
    @GetMapping(value = "getMyGrade/{id}")
    public CommonResult getMyGrade(HttpServletRequest request,
                                   @PathVariable("id") Long courseId){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        System.out.println("grade：userID："+userId);
        List<MyGradeVo> myGradeList = examinationService.getMyGrade(courseId,userId);
        System.out.println("myGradeList："+myGradeList);
        return CommonResult.ok().data("myGradeList",myGradeList);
    }

}
