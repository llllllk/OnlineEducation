package com.nwu.extra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.extra.client.BaseClient;
import com.nwu.extra.entity.Article;
import com.nwu.extra.mapper.ArticleMapper;
import com.nwu.extra.service.ArticleService;
import com.nwu.extra.vo.ArticleBackVo;
import com.nwu.extra.vo.ArticleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-10-29
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private BaseClient baseClient;

    @Override
    public Map<String, Object> pageQuery(Page<Article> pageParam, ArticleVo articleVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(articleVo.getCategoryId())) {
            if (!articleVo.getCategoryId().equals("-1")){
                queryWrapper.eq("category_id", articleVo.getCategoryId());
            }
        }
        if (!StringUtils.isBlank(articleVo.getTitle())) queryWrapper.like("title",articleVo.getTitle());
        if (!StringUtils.isBlank(articleVo.getGoodNum())) queryWrapper.orderByDesc("good_num");
        if (!StringUtils.isBlank(articleVo.getViewNum())) queryWrapper.orderByDesc("view_num");
        if (!StringUtils.isBlank(articleVo.getGmtModified())) queryWrapper.orderByDesc("gmt_modified");
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
    public Map<String, Object> pageQueryBack(Page<Article> pageParam, ArticleBackVo articleVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(articleVo.getCtgId())) {
            queryWrapper.eq("category_id", articleVo.getCtgId());
        }
        if (!StringUtils.isBlank(articleVo.getTitle())) queryWrapper.like("title",articleVo.getTitle());
        if (!StringUtils.isBlank(articleVo.getStatus())) queryWrapper.eq("status",articleVo.getStatus());
        if (!StringUtils.isBlank(articleVo.getName())){
            List<Long> user_ids = baseClient.getUidListByName(articleVo.getName());
            if (user_ids.size()<1){
                user_ids.add(-1L);
            }
            queryWrapper.in("user_id",user_ids);
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
    public Integer getNumByDate(String date) {
        return baseMapper.getNumByDate(date);
    }


}
