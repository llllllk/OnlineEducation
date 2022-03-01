package com.nwu.extra.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.extra.entity.Article;
import com.nwu.extra.vo.ArticleBackVo;
import com.nwu.extra.vo.ArticleVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-10-29
 */
public interface ArticleService extends IService<Article> {
    Map<String, Object> pageQuery(Page<Article> pageParam, ArticleVo articleVo);

    Map<String, Object> pageQueryBack(Page<Article> pageParam, ArticleBackVo articleVo);

    Integer getNumByDate(String date);
}
