package com.nwu.extra.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;

import com.nwu.extra.entity.Answer;
import com.nwu.extra.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-11-03
 */
@RestController
@RequestMapping("/extra/answers")
@CrossOrigin
public class AnswerController {
    @Autowired
    private AnswerService answerService;

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

    @DeleteMapping(value="delete/{id}")
    public CommonResult deleteAnswer(@PathVariable("id") Long id) {
        answerService.removeById(id);
        return CommonResult.ok();
    }

}

