package com.nwu.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.course.entity.Record;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-10-06
 */
public interface RecordService extends IService<Record> {
    String saveOrder(Long courseId, Long userId);
}
