package com.nwu.statistic.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);

        this.setFieldValByName("registerNum",0,metaObject);
        this.setFieldValByName("loginNum",0,metaObject);
        this.setFieldValByName("questionNum",0,metaObject);
        this.setFieldValByName("answerNum",0,metaObject);
        this.setFieldValByName("errorNum",0,metaObject);
        this.setFieldValByName("courseNum",0,metaObject);
        this.setFieldValByName("noteNum",0,metaObject);

        this.setFieldValByName("viewNum",0,metaObject);
        this.setFieldValByName("examNum",0,metaObject);
        this.setFieldValByName("fullNum",0,metaObject);
        this.setFieldValByName("collectNum",0,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}