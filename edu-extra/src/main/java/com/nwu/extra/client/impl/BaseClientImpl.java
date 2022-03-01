package com.nwu.extra.client.impl;

import com.nwu.common.vo.LoginInfoVo;
import com.nwu.extra.client.BaseClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public String getCategoryName(Integer id) {
        return null;
    }

    @Override
    public String getUserName(Long id) {
        return null;
    }

    @Override
    public LoginInfoVo getLoginInfoById(Long id) {
        return null;
    }

    @Override
    public List<Long> getUidListByName(String name) {
        return null;
    }
}
