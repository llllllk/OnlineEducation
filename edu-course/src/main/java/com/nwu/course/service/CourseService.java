package com.nwu.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.common.vo.FrontCourseVo;
import com.nwu.course.entity.Course;
import com.nwu.course.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
public interface CourseService extends IService<Course> {

    List<ChapterVo> getAllChapters(Long id);

    CourseVo getCourseConcreteInfo(Long id);

    IPage<Course> pageQuery(Page<Course> pageParam, CourseQueryVo courseQuery);

    boolean deleteCourseById(Long id);

    List<Course> getCourseByTeacherId(Long id);

    Map<String, Object> frontPageQuery(Page<Course> pageParam, FrontCourseQueryVo courseQuery);

    FrontOneCourseVo frontGetCourseInfo(Long id);

    void updateViewNum(Long id);

    Integer getNumByDate(String date);

    List<Course> getCoursesByIds(String ids);
    //根据课程ID集合获取课程并封装
    List<FrontCourseVo> getCourseVoByIds(List<Long> ids);

    void saveKeyWords(String keyWord);
}
