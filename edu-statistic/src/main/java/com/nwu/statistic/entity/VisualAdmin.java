package com.nwu.statistic.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class VisualAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    public VisualAdmin(Integer registerNum,Integer loginNum,
                       Integer questionNum,Integer answerNum,
                       Integer courseNum,Integer noteNum,
                       Integer errorNum,String dateCalculate){
        this.registerNum = registerNum;
        this.loginNum=loginNum;
        this.questionNum=questionNum;
        this.answerNum=answerNum;
        this.courseNum=courseNum;
        this.noteNum=noteNum;
        this.errorNum=errorNum;
        this.dateCalculate=dateCalculate;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //注册人数
    @TableField(fill = FieldFill.INSERT)
    private Integer registerNum;

    //登录人数
    @TableField(fill = FieldFill.INSERT)
    private Integer loginNum;

    //新增问题数
    @TableField(fill = FieldFill.INSERT)
    private Integer questionNum;

    //新增回答数
    @TableField(fill = FieldFill.INSERT)
    private Integer answerNum;

    //新增课程数
    @TableField(fill = FieldFill.INSERT)
    private Integer courseNum;

    //新增笔记数
    @TableField(fill = FieldFill.INSERT)
    private Integer noteNum;

    //每日异常数
    @TableField(fill = FieldFill.INSERT)
    private Integer errorNum;

    //统计日期
    private String dateCalculate;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
