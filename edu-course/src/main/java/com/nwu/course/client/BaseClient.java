package com.nwu.course.client;


import com.nwu.common.vo.LoginInfoVo;
import com.nwu.course.client.impl.BaseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-base",fallback = BaseClientImpl.class)
@Component
public interface BaseClient {
    @GetMapping(value = "/base/rest/users/getLoginInfoById/{id}")
    public LoginInfoVo getLoginInfoById(@PathVariable("id") Long id);

    //获取老师所在院系ID
    @GetMapping(value = "/base/rest/teachers/getDeptIdByTeacherId/{id}")
    public Integer getDeptIdByTeacherId(@PathVariable("id")Integer teacherId);
}
