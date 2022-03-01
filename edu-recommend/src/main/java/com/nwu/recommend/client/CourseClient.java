package com.nwu.recommend.client;

import com.nwu.common.entity.CommonResult;
import com.nwu.recommend.client.impl.CourseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-course",fallback = CourseClientImpl.class)
@Component
public interface CourseClient {

    @GetMapping(value = "/course/rest/courses/getCourseListByIds/{uList}/{iList}")
    public CommonResult getCourseListByIds(@PathVariable(value = "uList") String uList,
                                           @PathVariable(value = "iList") String iList);

    //获取这些用户选过的课程
    @GetMapping(value = "/course/rest/courses/getCourseIdsByUids/{uList}")
    public CommonResult getCourseIdsByUids(@PathVariable(value = "uList") String uList);

}
