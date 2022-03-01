package com.nwu.extra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nwu.extra.entity.Article;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lk
 * @since 2021-10-29
 */
public interface ArticleMapper extends BaseMapper<Article> {
    Integer getNumByDate(String myDate);
}
