package com.nwu.statistic.client;


import com.nwu.statistic.client.impl.CourseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-course",fallback = CourseClientImpl.class)
@Component
public interface CourseClient {
    @GetMapping(value = "/course/rest/courses/getNumByDate/{date}")
    public Integer getCourseNumByDate(@PathVariable("date") String date);
}
