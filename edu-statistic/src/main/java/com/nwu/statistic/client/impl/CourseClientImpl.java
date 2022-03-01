package com.nwu.statistic.client.impl;

import com.nwu.statistic.client.CourseClient;
import org.springframework.stereotype.Component;

@Component
public class CourseClientImpl implements CourseClient {
    @Override
    public Integer getCourseNumByDate(String date) {
        return 0;
    }
}
