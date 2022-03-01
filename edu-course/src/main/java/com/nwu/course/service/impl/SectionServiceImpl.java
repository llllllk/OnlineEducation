package com.nwu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.exception.MyException;

import com.nwu.course.client.CloudClient;
import com.nwu.course.entity.Chapter;
import com.nwu.course.entity.Course;
import com.nwu.course.entity.Section;
import com.nwu.course.mapper.SectionMapper;
import com.nwu.course.service.ChapterService;
import com.nwu.course.service.CourseService;
import com.nwu.course.service.SectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section> implements SectionService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CloudClient cloudClient;
    @Autowired
    private ChapterService chapterService;


    @Override
    public boolean sectionExist(Section section) {
        QueryWrapper<Section> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",section.getCourseId());
        wrapper.eq("chapter_id",section.getChapterId());
        wrapper.eq("sort",section.getSort());
        Integer integer = baseMapper.selectCount(wrapper);
        return integer>0;
    }

    @Override
    @Transactional
    public boolean removeSection(Long id) {
        Section section=baseMapper.selectById(id);
        if (!StringUtils.isBlank(section.getVideoId())){
            CommonResult commonResult = cloudClient.removeVideo(section.getVideoId());
            if (commonResult.getCode()==444){
                throw new MyException(444,"视频删除失败");
            }
        }
        int result = baseMapper.deleteById(id);
        Course course = courseService.getById(section.getCourseId());
        course.setLessonNum(course.getLessonNum()-1);
        courseService.updateById(course);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean deleteAllOfChapterId(Long id) {
        List<String> videoList=baseMapper.getVideoListByChapterId(id);
        CommonResult commonResult = cloudClient.removeVideoList(videoList);
        if (commonResult.getCode()==444){
            throw new MyException(444,"视频删除失败");
        }
        QueryWrapper<Section> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int delete = baseMapper.delete(wrapper);
        deleteLessonNum(id,videoList.size());
        return delete>=0;
    }

    @Override
    @Transactional
    public boolean deleteAllOfCourseId(Long id) {
        List<String> videoList=baseMapper.getVideoListByCourseId(id);
        CommonResult commonResult = cloudClient.removeVideoList(videoList);
        if (commonResult.getCode()==444){
            throw new MyException(444,"视频删除失败");
        }
        QueryWrapper<Section> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        int delete = baseMapper.delete(wrapper);
        deleteLessonNum(id,videoList.size());
        return delete>=0;
    }

    @Override
    public boolean addSection(Section section) {
        Course course = courseService.getById(section.getCourseId());
        course.setLessonNum(course.getLessonNum()+1);
        courseService.updateById(course);
        int insert = baseMapper.insert(section);
        return insert>0;
    }

    public void deleteLessonNum(Long chapterId,int num){
        Chapter chapter = chapterService.getById(chapterId);
        Course course = courseService.getById(chapter.getCourseId());
        course.setLessonNum(course.getLessonNum()-num);
        courseService.updateById(course);
    }
}
