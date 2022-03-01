package com.nwu.extra.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.common.vo.LoginInfoVo;
import com.nwu.extra.client.BaseClient;
import com.nwu.extra.entity.Answer;
import com.nwu.extra.service.AnswerService;
import com.nwu.extra.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/extra/front/answers")
@CrossOrigin
public class FrontAnswerController {
    @Autowired
    private BaseClient baseClient;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @GetMapping(value="pageList/{page}/{limit}/{questionId}")
    public CommonResult pageList(@PathVariable("page") Long page,
                                 @PathVariable("limit") Long limit,
                                 @PathVariable("questionId") Long questionId) {
        Page<Answer> answerPage = new Page<>(page, limit);
        IPage<Answer> iPage= answerService.pageQuery(answerPage,questionId);
        return CommonResult.ok().data("answers", iPage.getRecords()).data("total", iPage.getTotal());
    }

    @GetMapping(value = "myAnswer/{page}/{limit}")
    public CommonResult getMyAnswer(@PathVariable("page") Long page,
                                    @PathVariable("limit") Long limit,
                                    HttpServletRequest request){
        Long user_id = JwtUtil.getMemberIdByJwtToken(request);
        if (user_id==null) {
            return CommonResult.error().code(1001).message("请先登录！");
        }else {
            Page<Answer> questionPage = new Page<>(page, limit);
            IPage<Answer> iPage=answerService.getMyAnswer(questionPage, user_id);
            return CommonResult.ok().data("total", iPage.getTotal()).data("answerList", iPage.getRecords());
        }
    }

    @PostMapping(value = "add")
    public CommonResult addAnswer(@RequestBody Answer answer, HttpServletRequest request) {
        // 检查登录
        Long user_id = JwtUtil.getMemberIdByJwtToken(request);
        if (user_id==null) {
            return CommonResult.ok().code(1001).message("请先登录！");
        } else {
            // 调用用户中心微服务获取回答人相关信息
            LoginInfoVo loginInfo = baseClient.getLoginInfoById(user_id);
            if (loginInfo!=null) {
                // 设置回答人相关信息
                answer.setAvatar(loginInfo.getAvatar());
                answer.setName(loginInfo.getName());
                answer.setUserId(user_id);
                boolean success = answerService.save(answer);
                // 回答成功之后问题表回答数+1
                if (success) {
                    success = questionService.updateAnswerNum(answer.getQuestionId());
                }
                return success ? CommonResult.ok() : CommonResult.error().message("服务器错误！回答失败！");
            } else {
                return CommonResult.error().message("获取回答人信息失败！");
            }
        }
    }

    @DeleteMapping(value="delete/{id}")
    public CommonResult deleteAnswer(@PathVariable("id") Long id) {
        answerService.removeById(id);
        return CommonResult.ok();
    }
}
