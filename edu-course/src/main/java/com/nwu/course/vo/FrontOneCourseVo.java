package com.nwu.course.vo;

import lombok.Data;

@Data
public class FrontOneCourseVo {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Integer lessonNum;

    private String cover;

    private Long studyNum;

    private Long viewNum;

    private String description;

    private Integer teacherId;

    private String teacherName;

    private String intro;

    private String avatar;

    private Integer categoryId;

    private String categoryName;

    private String deptName;
}
