package com.nwu.course.client.impl;

import com.nwu.common.vo.LoginInfoVo;
import com.nwu.course.client.BaseClient;
import org.springframework.stereotype.Component;

@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public LoginInfoVo getLoginInfoById(Long id){
        return null;
    };

    @Override
    public Integer getDeptIdByTeacherId(Integer teacherId) {
        return null;
    }

}
