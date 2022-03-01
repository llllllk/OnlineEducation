package com.nwu.extra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.extra.entity.Examination;
import com.nwu.extra.vo.GradeVo;
import com.nwu.extra.vo.MyGradeVo;

import java.util.List;

/**
 * <p>
 * 测试卷 服务类
 * </p>
 *
 * @author lk
 * @since 2021-11-28
 */
public interface ExaminationService extends IService<Examination> {

    boolean deleteExam(Long id);

    double getTotal(GradeVo gradeVo, Long user_id);

    List<MyGradeVo> getMyGrade(Long courseId, Long user_id);
}
