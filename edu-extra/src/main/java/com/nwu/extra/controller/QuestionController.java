package com.nwu.extra.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.LoginInfoVo;
import com.nwu.extra.client.BaseClient;
import com.nwu.extra.entity.Question;
import com.nwu.extra.service.QuestionService;
import com.nwu.extra.vo.QuestionVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-11-03
 */
@RestController
@RequestMapping("/extra/questions")
@CrossOrigin
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping(value="pageList/{page}/{limit}")
    public CommonResult pageQuestion(@PathVariable("page") Integer page,
                                     @PathVariable("limit") Integer limit,
                                     @RequestBody(required = false) QuestionVo questionQuery) {
        Page<Question> questionPage = new Page<>(page, limit);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(questionQuery.getTitle())){
            wrapper.like("title",questionQuery.getTitle());
        }
        // 按照更新时间倒序查询
        wrapper.orderByDesc("gmt_create");
        questionService.page(questionPage, wrapper);
        return CommonResult.ok().data("total", questionPage.getTotal()).data("items", questionPage.getRecords());
    }

    @GetMapping(value = "getById/{id}")
    public CommonResult getQuestion(@PathVariable("id") Long id) {
        Question question = questionService.getById(id);
        if (question != null) {
            return CommonResult.ok().data("question", question);
        } else {
            return CommonResult.error().message("问题详情获取失败！");
        }
    }

    @DeleteMapping(value = "delete/{id}")
    public CommonResult deleteQuestion(@PathVariable("id") Long id){
        questionService.removeById(id);
        return CommonResult.ok();
    }
}

