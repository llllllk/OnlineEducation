package com.nwu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import com.nwu.course.entity.Comment;
import com.nwu.course.entity.Course;
import com.nwu.course.entity.MyDictionary;
import com.nwu.course.mapper.CommentMapper;
import com.nwu.course.service.CommentService;
import com.nwu.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-10-02
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private JiebaSegmenter jiebaSegmenter;

    @Autowired
    private MyDictionary myDictionary;

    @Override
    public Map<String, Object> getCommentsByCourseId(Page<Comment> pageParam, String courseId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam,wrapper);
        List<Comment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return map;
    }

    @Override
    public List<String> getTopSixComments(Long courseId) {
        QueryWrapper<Comment> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        Map<String,Integer> map=new HashMap<>();
        List<Comment> comments = baseMapper.selectList(wrapper);
        Course course = courseService.getById(courseId);
        String[] keys = course.getKeyWord().split(":");
        String type=null;
        for (Comment comment : comments) {
            List<SegToken> res=jiebaSegmenter.process( comment.getContent() , JiebaSegmenter.SegMode.INDEX);
            for (SegToken re : res) {
                type = myDictionary.getMap().getOrDefault(re.word,"null");
                if (type.equals("n") || type.equals("v")){
                    map.put(re.word,map.getOrDefault(re.word,0)+1);
                }
            }
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());
        entryList.sort((o1, o2) -> o2.getValue()-o1.getValue());
        List<String> result = new ArrayList<>(Arrays.asList(keys));
        for (Map.Entry<String, Integer> stringIntegerEntry : entryList) {
            if (result.size()==5) break;
            if (!result.contains(stringIntegerEntry.getKey())){
                result.add(stringIntegerEntry.getKey());
            }
        }
        return result;
    }
}
