package com.nwu.course.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author lk
 * @since 2021-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 课程封面
     */
    private String cover;

    /**
     * 描述
     */
    private String description;

    /**
     * 课时数
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer lessonNum;

    /**
     * 所属院系
     */
    private Integer deptId;

    /**
     * 课程类别id
     */
    private Integer courseCtgId;

    /**
     * 授课教师
     */
    private Integer teacherId;

    /**
     * 学习人数
     */
    @TableField(fill = FieldFill.INSERT)
    private Long studyNum;

    /**
     * 浏览数
     */
    @TableField(fill = FieldFill.INSERT)
    private Long viewNum;

    /**
     * 是否发布
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer status;


    private String keyWord;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
