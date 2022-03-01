package com.nwu.statistic.entity;

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
 * @since 2021-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VisualTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //课程id
    private Long courseId;

    //学习人数
    @TableField(fill = FieldFill.INSERT)
    private Integer viewNum;

    //添加人数
    @TableField(fill = FieldFill.INSERT)
    private Integer addNum;

    //提问人数
    @TableField(fill = FieldFill.INSERT)
    private Integer questionNum;

    //考试人数
    @TableField(fill = FieldFill.INSERT)
    private Integer examNum;

    //满分人数
    @TableField(fill = FieldFill.INSERT)
    private Integer fullNum;

    //收藏人数
    @TableField(fill = FieldFill.INSERT)
    private Integer collectNum;

    //统计日期
    private String dateCalculate;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
