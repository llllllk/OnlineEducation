package com.nwu.recommend.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nwu.common.entity.CommonResult;
import com.nwu.common.utils.JwtUtil;
import com.nwu.recommend.entity.Banner;
import com.nwu.recommend.service.BannerService;
import com.nwu.recommend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/recommend/front/main")
@CrossOrigin
public class IndexController {
    @Autowired
    private BannerService bannerService;

    @Autowired
    private RecommendService recommendService;

    @GetMapping(value = "getData")
    public CommonResult index(HttpServletRequest request) {
        Long user_id = JwtUtil.getMemberIdByJwtToken(request);
        QueryWrapper<Banner> wrapper=new QueryWrapper<>();
        wrapper.eq("type",0);
        List<Banner> list = bannerService.list(wrapper);

        CommonResult cr = recommendService.getListByUserId(user_id);
        return cr.data("bannerList", list);
    }
}
