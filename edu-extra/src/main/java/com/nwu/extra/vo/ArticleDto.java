package com.nwu.extra.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleDto {
    private Long id;

    private String title;

    private String content;

    private String keyWord;

    private Long viewNum;

    private Integer goodNum;

    private String name;

    private String category;

    private Date gmtModified;
}
