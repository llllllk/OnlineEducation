package com.nwu.extra.controller.front;

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

@RestController
@RequestMapping("/extra/front/questions")
@CrossOrigin
public class FrontQuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private BaseClient baseClient;

    @PostMapping(value="add")
    public CommonResult addQuestion(@RequestBody Question question,
                                    HttpServletRequest request) {
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null) {
            return CommonResult.error().code(1001).message("请先登录！");
        } else {
            // 调用用户中心微服务获取提问人相关信息
            LoginInfoVo loginInfo = baseClient.getLoginInfoById(userId);
            if (loginInfo!=null) {
                question.setAvatar(loginInfo.getAvatar());
                question.setName(loginInfo.getName());
                question.setUserId(userId);
                boolean success = questionService.save(question);
                return success ? CommonResult.ok() : CommonResult.error().message("服务器错误，提问失败！");
            } else {
                return CommonResult.error().message("获取提问人信息失败！");
            }
        }
    }

    @DeleteMapping(value = "delete/{id}")
    public CommonResult deleteQuestion(@PathVariable("id") Long id){
        questionService.removeById(id);
        return CommonResult.ok();
    }

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

    @GetMapping(value = "myQuestion")
    public CommonResult myQuestion(HttpServletRequest request) {
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        System.out.println("question：userID："+userId);
        if (userId==null) {
            return CommonResult.error().code(1001).message("请先登录！");
        } else {
            QueryWrapper<Question> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            List<Question> list = questionService.list(wrapper);
            return CommonResult.ok().data("questionList", list);
        }
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


}
