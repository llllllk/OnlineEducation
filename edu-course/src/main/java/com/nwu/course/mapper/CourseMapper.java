package com.nwu.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nwu.course.dto.CourseDetail;
import com.nwu.course.entity.Course;
import com.nwu.course.vo.CourseVo;
import com.nwu.course.vo.FrontOneCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
public interface CourseMapper extends BaseMapper<Course> {
    //获取所有章节信息
    List<CourseDetail> getAllChapters(@Param("id") Long id);

    CourseVo getCourseConcreteInfo(@Param("id")Long id);

    FrontOneCourseVo frontGetCourseInfo(Long id);

    Integer getNumByDate(String myDate);
}
