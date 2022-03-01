package com.nwu.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nwu.course.entity.Section;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
public interface SectionMapper extends BaseMapper<Section> {
    List<String> getVideoListByChapterId(Long id);

    List<String> getVideoListByCourseId(Long id);
}
