package com.nwu.extra.controller.rest;

import com.nwu.extra.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extra/rest/questions")
@CrossOrigin
public class RestQuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "getNumByDate/{date}")
    public Integer getQuestionNumByDate(@PathVariable("date") String date){
        return questionService.getNumByDate(date);
    }
}
