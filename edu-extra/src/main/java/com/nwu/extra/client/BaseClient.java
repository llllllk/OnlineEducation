package com.nwu.extra.client;


import com.nwu.common.vo.FrontCourseVo;
import com.nwu.common.vo.LoginInfoVo;
import com.nwu.extra.client.impl.BaseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-base",fallback = BaseClientImpl.class)
@Component
public interface BaseClient {

//    @GetMapping(value = "/base/rest/teachers/getTeacher/{id}")
//    public Integer getTeacher(@PathVariable("id") Long id);
//
    //根据类别ID获取类别名称
    @GetMapping(value = "/base/rest/categories/getName/{id}")
    public String getCategoryName(@PathVariable("id") Integer id);
//
//    //根据id集合获取课程
//    @GetMapping(value = "/base/rest/courses/frontGetMyCourse")
//    public List<FrontCourseVo> getCourse(@RequestParam("ids") List<Long> ids);
//

    //根据用户ID获取用户姓名
    @GetMapping(value = "/base/rest/users/getUserName/{id}")
    public String getUserName(@PathVariable("id")Long id);

    //根据用户ID获取用户登录信息
    @GetMapping(value = "/base/rest/users/getLoginInfoById/{id}")
    public LoginInfoVo getLoginInfoById(@PathVariable("id") Long id);

    //获取名字为xx的所有用户ID
    @GetMapping(value = "/base/rest/users/getUidListByName/{name}")
    public List<Long> getUidListByName(@PathVariable("name") String name);
}
