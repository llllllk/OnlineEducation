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
 * 
 * </p>
 *
 * @author lk
 * @since 2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //标题
    private String title;

    //内容
    private String content;

    //文章内容关键字
    private String keyWord;

    //浏览数
    @TableField(fill = FieldFill.INSERT)
    private Long viewNum;

    //点赞数
    @TableField(fill = FieldFill.INSERT)
    private Integer goodNum;

    //发布者
    private Long userId;

    //所属类别
    private Integer categoryId;

    //发布状态
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
