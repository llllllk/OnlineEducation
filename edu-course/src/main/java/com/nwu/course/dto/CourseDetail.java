package com.nwu.course.dto;

import lombok.Data;

@Data
public class CourseDetail {
    private Long chapterId;
    private String chapterName;
    private Integer cpSort;
    private Long sectionId;
    private String sectionName;
    private String videoId;
    private Integer sSort;
}
