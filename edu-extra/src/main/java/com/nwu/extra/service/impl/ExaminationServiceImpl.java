package com.nwu.extra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.extra.entity.Examination;
import com.nwu.extra.entity.Grade;
import com.nwu.extra.entity.Subject;
import com.nwu.extra.mapper.ExaminationMapper;
import com.nwu.extra.service.ExaminationService;
import com.nwu.extra.service.GradeService;
import com.nwu.extra.service.SubjectService;
import com.nwu.extra.vo.GradeVo;
import com.nwu.extra.vo.MyGradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 测试卷 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-11-28
 */
@Service
public class ExaminationServiceImpl extends ServiceImpl<ExaminationMapper, Examination> implements ExaminationService {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private GradeService gradeService;

    @Override
    @Transactional
    public boolean deleteExam(Long id) {
        boolean flag = subjectService.deleteByExamId(id);
        int i = baseMapper.deleteById(id);
        return flag && i>0;
    }

    @Override
    public double getTotal(GradeVo gradeVo, Long user_id) {
        Examination examination = baseMapper.selectById(gradeVo.getExaminationId());
        List<Subject> subLists = subjectService.getSubListByExamId(gradeVo.getExaminationId());
        double total = examination.getTotal();
        int num = subLists.size();
        double every = total/num;
        BigDecimal b = new BigDecimal(every);
        every= b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        for (int i=0;i<subLists.size();i++){
            if (!subLists.get(i).getRightAnswer().equals(gradeVo.getValue()[i])){
                total -= every;
                num--;
            }
        }
        total = total>=every?total:0;
        Grade gradeInfo = new Grade();
        gradeInfo.setExaminationId(gradeVo.getExaminationId());
        gradeInfo.setUserId(user_id);
        gradeInfo.setTotal(total);
        gradeInfo.setRightNum(num);
        gradeService.save(gradeInfo);
        return total;
    }

    @Override
    public List<MyGradeVo> getMyGrade(Long courseId, Long user_id) {
        QueryWrapper<Examination> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<Examination> examinations = baseMapper.selectList(wrapper);
        System.out.println(examinations);
        Map<Long,String> NameMap = new HashMap<>();
        for (Examination examination : examinations) {
            NameMap.put(examination.getId(),examination.getName());
        }
        QueryWrapper<Grade> wrapper1=new QueryWrapper<>();
        wrapper1.eq("user_id",user_id);
        if (NameMap.size()==0) return new ArrayList<>();
        wrapper1.in("examination_id",NameMap.keySet());
        wrapper1.orderByDesc("gmt_create");
        List<Grade> gradeList = gradeService.list(wrapper1);
        Map<Long,Double> map = new HashMap<>();
        for (Grade grade : gradeList) {
            if (map.containsKey(grade.getExaminationId())){
                if (grade.getTotal()>map.get(grade.getExaminationId())){
                    map.put(grade.getExaminationId(),grade.getTotal());
                }
            }else {
                map.put(grade.getExaminationId(),grade.getTotal());
            }
        }
        List<MyGradeVo> list=new ArrayList<>();
        for (Long examId : map.keySet()) {
            MyGradeVo myGradeVo=new MyGradeVo();
            myGradeVo.setName(NameMap.get(examId));
            myGradeVo.setTotal(map.get(examId));
            list.add(myGradeVo);
        }
        return list;
    }

}
