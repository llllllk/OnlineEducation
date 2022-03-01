package com.nwu.course.vo;

import lombok.Data;

@Data
public class CourseVo {
    private Long id;
    private String name;
    private String cover;
    private String category;
    private String teacher;
    private String department;
}
