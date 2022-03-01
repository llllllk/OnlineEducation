package com.nwu.common.vo;

import lombok.Data;


@Data
public class FrontCourseVo {
    private Long id;
    private String name;
    private String cover;
    private String description;
    private Integer lessonNum;
    private Long studyNum;
    private Long viewNum;
    private String keyWord;
}
