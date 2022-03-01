package com.nwu.base.client;


import com.nwu.base.client.impl.CourseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "service-course",fallback = CourseClientImpl.class)
@Component
public interface CourseClient {


}
