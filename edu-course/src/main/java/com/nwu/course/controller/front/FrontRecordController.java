package com.nwu.course.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.FrontCourseVo;
import com.nwu.course.entity.Course;
import com.nwu.course.entity.Record;
import com.nwu.course.service.CourseService;
import com.nwu.course.service.RecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/course/front/records")
@CrossOrigin
public class FrontRecordController {
    @Autowired
    private RecordService recordService;

    @Autowired
    private CourseService courseService;

    @PostMapping(value = "createRecord/{courseId}")
    public CommonResult save(@PathVariable(value = "courseId") Long courseId, HttpServletRequest request) {
        Long userId= JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null) return CommonResult.error().code(1001).message("请登录后再添加课程");
        String orderId = recordService.saveOrder(courseId, userId);
        return CommonResult.ok().data("orderId", orderId);
    }

    //获取对应的记录
    @GetMapping(value = "getRecord/{recordId}")
    public CommonResult getOrder(@PathVariable String recordId) {
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        wrapper.eq("record_id",recordId);
        Record record = recordService.getOne(wrapper);
        return CommonResult.ok().data("item", record);
    }

    //获取我的课程
    @GetMapping(value = "myCourse")
    public CommonResult getMyCourse(HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null){
            return CommonResult.error().code(1001).message("请先登录！");
        }else {
            QueryWrapper<Record> wrapper=new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            List<Record> list = recordService.list(wrapper);
            List<Long> ids=new ArrayList<>();
            for (Record record : list) {
                ids.add(record.getCourseId());
            }
            List<FrontCourseVo> courseVoList = courseService.getCourseVoByIds(ids);
            return CommonResult.ok().data("recordList",courseVoList);
        }
    }

    //删除该门选课记录
    @DeleteMapping(value = "delete/{courseId}")
    public CommonResult deleteRecord(@PathVariable("courseId") Long courseId,
                                     HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null){
            return CommonResult.error().code(1001).message("请先登录！");
        }else{
            QueryWrapper<Record> wrapper=new QueryWrapper<>();
            wrapper.eq("course_id",courseId);
            wrapper.eq("user_id",userId);
            recordService.remove(wrapper);
            return CommonResult.ok();
        }
    }

    //获取是否已选该门课程
    @GetMapping(value = "getRecordStatus/{courseId}")
    public CommonResult getRecordStatus(@PathVariable String courseId,
                                        HttpServletRequest request) {
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("user_id",userId);
        Record record = recordService.getOne(wrapper);
        return CommonResult.ok().data("isAdd", record!=null);
    }

    @GetMapping(value = "getAllUserRecord")
    public CommonResult getAllUserRecord(){
        List<Record> recordList = recordService.list(null);
        Map<Long, Set<Long>> userInfo = new HashMap<>();
        Map<Long, Set<Long>> itemInfo = new HashMap<>();
        for (Record record : recordList) {
            Long cid=record.getCourseId();
            Long uid=record.getUserId();
            if (!userInfo.containsKey(uid)){
                userInfo.put(uid,new HashSet<>());
            }
            userInfo.get(uid).add(cid);
            if (!itemInfo.containsKey(cid)){
                itemInfo.put(cid,new HashSet<>());
            }
            itemInfo.get(cid).add(uid);
        }
        return CommonResult.ok().data("userInfo",userInfo).data("itemInfo",itemInfo);
    }
}
