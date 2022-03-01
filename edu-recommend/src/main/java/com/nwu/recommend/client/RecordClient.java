package com.nwu.recommend.client;

import com.nwu.common.entity.CommonResult;
import com.nwu.recommend.client.impl.RecordClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-record",fallback = RecordClientImpl.class)
@Component
public interface RecordClient {
    @GetMapping(value = "/record/records/getAllUserRecord")
    public CommonResult getAllUserRecord();
}
