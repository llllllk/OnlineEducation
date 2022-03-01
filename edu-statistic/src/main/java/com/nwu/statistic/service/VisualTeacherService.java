package com.nwu.statistic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.statistic.entity.VisualTeacher;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-12-11
 */
public interface VisualTeacherService extends IService<VisualTeacher> {

    Map<String, Object> showFigure(String courseId, String day);

    Map<String, Object> showTrend(String courseId, String type, String begin, String end);
}
