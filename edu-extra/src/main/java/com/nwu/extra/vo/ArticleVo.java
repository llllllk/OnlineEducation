package com.nwu.extra.vo;

import lombok.Data;


@Data
public class ArticleVo {
    private String title;
    private String viewNum;
    private String goodNum;
    private String gmtModified;
    private String categoryId;
}
