package com.nwu.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.course.entity.Chapter;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
public interface ChapterService extends IService<Chapter> {

    boolean removeChapterById(Long id);

    boolean sortExist(Chapter chapter);

    boolean deleteByCourseId(Long id);
}
