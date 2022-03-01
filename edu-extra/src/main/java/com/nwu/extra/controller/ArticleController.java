package com.nwu.extra.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.extra.client.BaseClient;
import com.nwu.extra.entity.Article;
import com.nwu.extra.service.ArticleService;
import com.nwu.extra.vo.ArticleBackVo;
import com.nwu.extra.vo.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-10-29
 */
@RestController
@RequestMapping("/extra/articles")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private BaseClient baseClient;

    @GetMapping(value = "getAll")
    public CommonResult getAll(){
        List<Article> list = articleService.list(null);
        return CommonResult.ok().data("articleList",list);
    }
    //分页获取全部笔记
    @PostMapping(value = "pageList/{page}/{limit}")
    public CommonResult pageList(@PathVariable("page") Integer page,
                                 @PathVariable("limit") Integer limit,
                                 @RequestBody(required = false) ArticleBackVo articleVo){
        Page<Article> pageParam = new Page<>(page, limit);
        Map<String, Object> map = articleService.pageQueryBack(pageParam,articleVo);
        return CommonResult.ok().data(map);
    }

    //删除笔记
    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        articleService.removeById(id);
        return CommonResult.ok();
    }

    //获取笔记详情
    @GetMapping(value = "getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        Article article = articleService.getById(id);
        String categoryName = baseClient.getCategoryName(article.getCategoryId());
        String userName = baseClient.getUserName(article.getUserId());
        ArticleDto articleDto=exchange(article,categoryName,userName);
        return CommonResult.ok().data("article",articleDto);
    }

    //下架或者上架某个笔记
    @GetMapping(value = "changeStatus/{id}/{value}")
    public CommonResult changeStatus(@PathVariable("id") Long id,
                                @PathVariable("value") Integer value){
        Article article = articleService.getById(id);
        article.setStatus(value);
        articleService.updateById(article);
        return CommonResult.ok();
    }

    public ArticleDto exchange(Article article,String categoryName,String userName){
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setCategory(categoryName);
        articleDto.setViewNum(article.getViewNum());
        articleDto.setGmtModified(article.getGmtModified());
        articleDto.setGoodNum(article.getGoodNum());
        articleDto.setName(userName);
        articleDto.setKeyWord(article.getKeyWord());
        return articleDto;
    }



}

