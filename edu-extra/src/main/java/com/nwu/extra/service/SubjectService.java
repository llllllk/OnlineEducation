package com.nwu.extra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.extra.entity.Subject;

import java.util.List;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author lk
 * @since 2021-11-28
 */
public interface SubjectService extends IService<Subject> {

    boolean deleteByExamId(Long id);

    List<Subject> getSubListByExamId(Long id);

}
