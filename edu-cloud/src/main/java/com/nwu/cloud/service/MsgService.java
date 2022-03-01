package com.nwu.cloud.service;

import java.util.Map;

public interface MsgService {
    public boolean send(String PhoneNumbers, Map<String,Object> param);
}
