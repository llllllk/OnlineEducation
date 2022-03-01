package com.nwu.course.vo;

import lombok.Data;

@Data
public class CourseQueryVo {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer teacherId;

    private Integer ctgId;

    private Integer deptId;
}
