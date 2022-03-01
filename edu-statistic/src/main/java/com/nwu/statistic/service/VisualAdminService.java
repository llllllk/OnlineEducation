package com.nwu.statistic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.statistic.entity.VisualAdmin;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-12-11
 */
public interface VisualAdminService extends IService<VisualAdmin> {

    Map<String, Object> showFigureOfDay(String day);

    Map<String, Object> showFigureOfMonth(String month);

    Map<String, Object> showTrendOfDay(String begin, String end, String type);

    Map<String, Object> showTrendOfMonth(String begin, String end, String type);

    void createData(String data);
}
