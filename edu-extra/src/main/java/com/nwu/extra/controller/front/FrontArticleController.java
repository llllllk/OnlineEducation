package com.nwu.extra.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.extra.client.BaseClient;
import com.nwu.extra.entity.Article;
import com.nwu.extra.service.ArticleService;
import com.nwu.extra.vo.ArticleDto;
import com.nwu.extra.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/extra/front/articles")
@CrossOrigin
public class FrontArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BaseClient baseClient;

    @PostMapping(value = "pageList/{page}/{limit}")
    public CommonResult pageList(@PathVariable("page") Integer page,
                                 @PathVariable("limit") Integer limit,
                                 @RequestBody(required = false) ArticleVo articleVo){
        Page<Article> pageParam = new Page<>(page, limit);
        Map<String, Object> map=articleService.pageQuery(pageParam, articleVo);
        return CommonResult.ok().data(map);
    }
    //查看某个文章
    @GetMapping(value = "getById/{id}")
    public CommonResult getArticle(@PathVariable("id") Long id){
        Article article = articleService.getById(id);
        article.setViewNum(article.getViewNum()+1);
        articleService.updateById(article);
        String categoryName = baseClient.getCategoryName(article.getCategoryId());
        String userName = baseClient.getUserName(article.getUserId());
        ArticleDto articleDto=exchange(article,categoryName,userName);
        return CommonResult.ok().data("item",articleDto);
    }

    //添加文章
    @PostMapping(value = "add")
    public CommonResult save(@RequestBody Article article,HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        if (userId==null) return CommonResult.error().code(1001).message("请重新登录");
        article.setUserId(userId);
        articleService.save(article);
        return CommonResult.ok().message("保存成功");
    }

    @DeleteMapping(value = "delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        articleService.removeById(id);
        return CommonResult.ok();
    }

    @GetMapping(value = "myArticle")
    public CommonResult getMyArticle(HttpServletRequest request){
        Long userId = JwtUtil.getMemberIdByJwtToken(request);
        System.out.println("article：userID："+userId);
        if (userId==null) {
            return CommonResult.error().code(1001).message("请先登录！");
        }else {
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            List<Article> list = articleService.list(wrapper);
            return CommonResult.ok().data("articleList", list);
        }

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
