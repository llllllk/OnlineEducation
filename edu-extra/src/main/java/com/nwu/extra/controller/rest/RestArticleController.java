package com.nwu.extra.controller.rest;

import com.nwu.extra.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extra/rest/articles")
@CrossOrigin
public class RestArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "getNumByDate/{date}")
    public Integer getArticleNumByDate(@PathVariable("date") String date){
        return articleService.getNumByDate(date);
    }



}
