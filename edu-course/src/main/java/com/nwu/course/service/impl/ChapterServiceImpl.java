package com.nwu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nwu.course.entity.Chapter;
import com.nwu.course.mapper.ChapterMapper;
import com.nwu.course.service.ChapterService;
import com.nwu.course.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private SectionService sectionService;

    @Override
    @Transactional
    public boolean removeChapterById(Long id) {
        boolean remove = sectionService.deleteAllOfChapterId(id);
        int i = baseMapper.deleteById(id);
        return i >= 0 && remove;
    }

    @Override
    public boolean sortExist(Chapter chapter) {
        QueryWrapper<Chapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",chapter.getCourseId());
        wrapper.eq("sort",chapter.getSort());
        Integer integer = baseMapper.selectCount(wrapper);
        return integer>0;
    }

    @Override
    public boolean deleteByCourseId(Long id) {
        QueryWrapper<Chapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",id);
        return baseMapper.delete(wrapper)>=0;
    }


}
