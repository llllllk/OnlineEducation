package com.nwu.course.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChapterVo {
    private Long id;
    private String name;
    private Integer sort;
    private List<SectionVo> sectionList=new ArrayList<>();
    public ChapterVo(Long id,String name){
        this.id=id;
        this.name=name;
    }
}
