package com.nwu.recommend.client.impl;


import com.nwu.common.entity.CommonResult;
import com.nwu.recommend.client.CourseClient;
import org.springframework.stereotype.Component;

@Component
public class CourseClientImpl implements CourseClient {
    @Override
    public CommonResult getCourseListByIds(String uList, String iList) {
        return null;
    }

    @Override
    public CommonResult getCourseIdsByUids(String uList) {
        return null;
    }
}
