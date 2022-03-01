package com.nwu.recommend.client;



import com.nwu.common.entity.CommonResult;
import com.nwu.recommend.client.impl.CourseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-security",fallback = CourseClientImpl.class)
@Component
public interface BaseClient {
    @GetMapping(value = "/security/user/getUserDepartment")
    public CommonResult getUserDepartment();
}
