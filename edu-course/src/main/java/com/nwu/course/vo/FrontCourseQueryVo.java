package com.nwu.course.vo;

import lombok.Data;

@Data
public class FrontCourseQueryVo {

    private static final long serialVersionUID = 1L;

    private String name;

    private String teacherId;

    private String courseCtgId;

    private String viewNum;

    private String studyNum;

    private String gmtCreate;
}
