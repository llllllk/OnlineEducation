package com.nwu.extra.controller.rest;

import com.nwu.extra.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extra/rest/answers")
@CrossOrigin
public class RestAnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping(value = "getNumByDate/{date}")
    public Integer getAnswerNumByDate(@PathVariable("date") String date){
        return answerService.getNumByDate(date);
    }
}
