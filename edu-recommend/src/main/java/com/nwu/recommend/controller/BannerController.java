package com.nwu.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nwu.common.entity.CommonResult;
import com.nwu.recommend.entity.Banner;
import com.nwu.recommend.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-09-04
 */
@RestController
@RequestMapping("/recommend/banner")
@CrossOrigin
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping(value = "pageListCourse/{page}/{limit}")
    public CommonResult pageListCourse(@PathVariable(value = "page") Long page,
                              @PathVariable(value = "limit") Long limit) {
        Page<Banner> pageParam = new Page<>(page, limit);
        QueryWrapper<Banner> wrapper=new QueryWrapper<>();
        wrapper.eq("type",0);
        bannerService.page(pageParam,wrapper);
        return CommonResult.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @GetMapping(value = "pageListArticle/{page}/{limit}")
    public CommonResult pageListArticle(@PathVariable(value = "page") Long page,
                                 @PathVariable(value = "limit") Long limit) {
        Page<Banner> pageParam = new Page<>(page, limit);
        QueryWrapper<Banner> wrapper=new QueryWrapper<>();
        wrapper.eq("type",1);
        bannerService.page(pageParam,wrapper);
        return CommonResult.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }


    @GetMapping(value = "get/{id}")
    public CommonResult get(@PathVariable(value = "id") Long id) {
        Banner banner = bannerService.getById(id);
        return CommonResult.ok().data("item", banner);
    }

    @PostMapping(value = "save")
    public CommonResult save(@RequestBody Banner banner) {
        bannerService.save(banner);
        return CommonResult.ok();
    }

    @PutMapping(value = "update")
    public CommonResult updateById(@RequestBody Banner banner) {
        bannerService.updateById(banner);
        return CommonResult.ok();
    }

    @DeleteMapping(value = "remove/{id}")
    public CommonResult remove(@PathVariable(value = "id") Long id) {
        bannerService.removeById(id);
        return CommonResult.ok();
    }

}

