package com.nwu.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.course.entity.Section;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
public interface SectionService extends IService<Section> {

    boolean sectionExist(Section section);

    boolean removeSection(Long id);

    boolean deleteAllOfChapterId(Long id);

    boolean deleteAllOfCourseId(Long id);

    boolean addSection(Section section);
}
