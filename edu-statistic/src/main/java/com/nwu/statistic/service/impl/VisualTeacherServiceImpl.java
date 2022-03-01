package com.nwu.statistic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.statistic.entity.VisualTeacher;
import com.nwu.statistic.mapper.VisualTeacherMapper;
import com.nwu.statistic.service.VisualTeacherService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-12-11
 */
@Service
public class VisualTeacherServiceImpl extends ServiceImpl<VisualTeacherMapper, VisualTeacher> implements VisualTeacherService {

    @Override
    public Map<String, Object> showFigure(String courseId, String day) {
        QueryWrapper<VisualTeacher> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("date_calculate",day);
        VisualTeacher visualTeacher = baseMapper.selectOne(wrapper);
        Map<String,Object> map=new HashMap<>();
        map.put("typeList", Arrays.asList(
                "添加数",
                "收藏数",
                "查看数",
                "考试人数",
                "满分人数",
                "提问人数"));
        if (visualTeacher==null) return map;
        map.put("dataList",Arrays.asList(
                visualTeacher.getAddNum(),
                visualTeacher.getCollectNum(),
                visualTeacher.getViewNum(),
                visualTeacher.getExamNum(),
                visualTeacher.getFullNum(),
                visualTeacher.getQuestionNum()));
        return map;
    }

    @Override
    public Map<String, Object> showTrend(String courseId, String type, String begin, String end) {
        QueryWrapper<VisualTeacher> wrapper=new QueryWrapper<>();
        wrapper.between("date_calculate",begin,end);
        wrapper.eq("course_id",courseId);
        List<VisualTeacher> visualTeachers = baseMapper.selectList(wrapper);
        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        for (VisualTeacher visualTeacher : visualTeachers) {
            dateList.add(visualTeacher.getDateCalculate());
            switch (type) {
                case "view_num":
                    dataList.add(visualTeacher.getViewNum());
                    break;
                case "add_num":
                    dataList.add(visualTeacher.getAddNum());
                    break;
                case "question_num":
                    dataList.add(visualTeacher.getQuestionNum());
                    break;
                case "exam_num":
                    dataList.add(visualTeacher.getExamNum());
                    break;
                case "full_num":
                    dataList.add(visualTeacher.getFullNum());
                    break;
                case "collect_num":
                    dataList.add(visualTeacher.getCollectNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
