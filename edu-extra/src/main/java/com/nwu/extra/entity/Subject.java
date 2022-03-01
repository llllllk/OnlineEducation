package com.nwu.extra.entity;

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
 * 题目表
 * </p>
 *
 * @author lk
 * @since 2021-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    /**
     * 选项A描述
     */
    private String selectA;

    private String selectB;

    private String selectC;

    private String selectD;

    /**
     * 正确答案
     */
    private String rightAnswer;


    //题目编号
    private Integer sort;

    /**
     * 对应的试卷编号
     */
    private Long examinationId;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
