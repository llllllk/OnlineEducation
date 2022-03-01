package com.nwu.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.common.utils.RandomUtil;
import com.nwu.course.entity.Course;
import com.nwu.course.entity.Record;
import com.nwu.course.mapper.RecordMapper;
import com.nwu.course.service.CourseService;
import com.nwu.course.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private CourseService courseService;

    @Override
    public String saveOrder(Long courseId, Long userId) {
        Course course = courseService.getById(courseId);
        Record record = new Record();
        record.setTeacherId(course.getTeacherId());
        record.setCourseId(courseId);
        record.setUserId(userId);
        String orderNo= RandomUtil.getOrderNo();
        record.setRecordId(orderNo);
        baseMapper.insert(record);
        return orderNo;
    }
}
