package com.nwu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nwu.common.vo.FrontCourseVo;
import com.nwu.course.client.BaseClient;
import com.nwu.course.dto.CourseDetail;
import com.nwu.course.entity.Course;
import com.nwu.course.mapper.CourseMapper;
import com.nwu.course.service.ChapterService;
import com.nwu.course.service.CourseService;
import com.nwu.course.service.SectionService;
import com.nwu.course.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private BaseClient baseClient;

    @Override
    public List<ChapterVo> getAllChapters(Long id) {
        List<ChapterVo> info=new ArrayList<>();
        Map<Long,List<SectionVo>> map=new HashMap<>();
        List<CourseDetail> res=baseMapper.getAllChapters(id);
        for (CourseDetail re : res) {
            if (!map.containsKey(re.getChapterId())){
                ChapterVo chapterVo=new ChapterVo();
                chapterVo.setId(re.getChapterId());
                chapterVo.setName(re.getChapterName());
                chapterVo.setSort(re.getCpSort());
                List<SectionVo> list=new ArrayList<>();
                chapterVo.setSectionList(list);
                map.put(re.getChapterId(),list);
                info.add(chapterVo);
            }
            if (re.getSectionId()==null) continue;
            SectionVo sectionVo=new SectionVo();
            sectionVo.setId(re.getSectionId());
            sectionVo.setName(re.getSectionName());
            sectionVo.setVideoId(re.getVideoId());
            sectionVo.setSort(re.getSSort());
            map.get(re.getChapterId()).add(sectionVo);
        }
        return info;
    }

    @Override
    public CourseVo getCourseConcreteInfo(Long id) {
        return baseMapper.getCourseConcreteInfo(id);
    }

    @Override
    public IPage<Course> pageQuery(Page<Course> pageParam, CourseQueryVo courseQuery) {
        if (courseQuery==null) return baseMapper.selectPage(pageParam, null);
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("status",1);
        if (!StringUtils.isBlank(courseQuery.getName())){
            wrapper.eq("name",courseQuery.getName());
        }
        if (courseQuery.getCtgId()!=null){
            wrapper.eq("course_ctg_id",courseQuery.getCtgId());
        }
        if (courseQuery.getDeptId()!=null){
            wrapper.eq("dept_id",courseQuery.getDeptId());
        }
        if (courseQuery.getTeacherId()!=null){
            wrapper.eq("teacher_id",courseQuery.getTeacherId());
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }

    @Override
    @Transactional
    public boolean deleteCourseById(Long id) {
        boolean flag1 = sectionService.deleteAllOfCourseId(id);
        boolean flag2 = chapterService.deleteByCourseId(id);
        int i = baseMapper.deleteById(id);
        return flag1 && flag2 && i > 0;
    }

    @Override
    public List<Course> getCourseByTeacherId(Long id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();
        queryWrapper.eq("teacher_id", id);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> frontPageQuery(Page<Course> pageParam, FrontCourseQueryVo courseQuery) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(courseQuery.getCourseCtgId())) {
            if (!courseQuery.getCourseCtgId().equals("-1")){
                queryWrapper.eq("course_ctg_id", courseQuery.getCourseCtgId());
            }
        }
        if (!StringUtils.isBlank(courseQuery.getStudyNum())) {
            queryWrapper.orderByDesc("study_num");
        }
        if (!StringUtils.isBlank(courseQuery.getViewNum())) {
            queryWrapper.orderByDesc("view_num");
        }

        if (!StringUtils.isBlank(courseQuery.getGmtCreate())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", pageParam.getRecords());
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return map;
    }

    @Override
    public FrontOneCourseVo frontGetCourseInfo(Long id) {
        FrontOneCourseVo courseVo = baseMapper.frontGetCourseInfo(id);
        return courseVo;
//        Course course = baseMapper.selectById(id);
//        Teacher teacher = teacherService.getById(course.getTeacherId());
//        CourseCategory category = categoryService.getById(course.getCourseCtgId());
//        return transform(course,teacher,category);
    }

    @Override
    public void updateViewNum(Long id) {
        Course course = baseMapper.selectById(id);
        course.setViewNum(course.getViewNum()+1);
        baseMapper.updateById(course);
    }

    @Override
    public Integer getNumByDate(String date) {
        return baseMapper.getNumByDate(date);
    }

    @Override
    public List<Course> getCoursesByIds(String ids) {
        String[] idList=ids.split(":");
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.in("id", Arrays.asList(idList));
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FrontCourseVo> getCourseVoByIds(List<Long> ids) {
        Collection<Course> courses = baseMapper.selectBatchIds(ids);
        List<FrontCourseVo> courseVoList = new ArrayList<>();
        for (Course course : courses) {
            FrontCourseVo vo=new FrontCourseVo();
            BeanUtils.copyProperties(course,vo);
            courseVoList.add(vo);
        }
        return courseVoList;
    }

//    private FrontOneCourseVo transform(Course course, Teacher teacher, CourseCategory category) {
//        FrontOneCourseVo courseVo=new FrontOneCourseVo();
//        courseVo.setId(course.getId());
//        courseVo.setCover(course.getCover());
//        courseVo.setDescription(course.getDescription());
//        courseVo.setName(course.getName());
//        courseVo.setLessonNum(course.getLessonNum());
//        courseVo.setStudyNum(course.getStudyNum());
//        courseVo.setViewNum(course.getViewNum());
//
//        courseVo.setCategoryId(course.getCourseCtgId());
//        courseVo.setCategoryName(category.getName());
//
//        courseVo.setTeacherId(course.getTeacherId());
//        courseVo.setTeacherName(teacher.getName());
//        courseVo.setAvatar(teacher.getAvatar());
//        courseVo.setIntro(teacher.getDescription());
//        return courseVo;
//    }
}
