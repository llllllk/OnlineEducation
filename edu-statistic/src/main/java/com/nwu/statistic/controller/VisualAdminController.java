package com.nwu.statistic.controller;


import com.nwu.common.entity.CommonResult;
import com.nwu.statistic.service.VisualAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lk
 * @since 2021-12-11
 */
@RestController
@RequestMapping("/statistic/admin")
@CrossOrigin
public class VisualAdminController {

    @Autowired
    private VisualAdminService visualAdminService;

    @GetMapping(value = "showFigureOfDay/{day}")
    public CommonResult showFigureOfDay(@PathVariable String day) {
        Map<String, Object> map = visualAdminService.showFigureOfDay(day);
        return CommonResult.ok().data(map);
    }

    @GetMapping(value = "showFigureOfMonth/{month}")
    public CommonResult showFigureOfMonth(@PathVariable String month) {
        Map<String, Object> map = visualAdminService.showFigureOfMonth(month);
        return CommonResult.ok().data(map);
    }

    @GetMapping(value = "showTrendOfDay/{begin}/{end}/{type}")
    public CommonResult showTrendOfDay(@PathVariable String begin,
                                       @PathVariable String end,
                                       @PathVariable String type){
        Map<String, Object> map = visualAdminService.showTrendOfDay(begin, end, type);
        return CommonResult.ok().data(map);
    }

    @GetMapping(value = "showTrendOfMonth/{begin}/{end}/{type}")
    public CommonResult showTrendOfMonth(@PathVariable String begin,
                                       @PathVariable String end,
                                       @PathVariable String type){
        Map<String, Object> map = visualAdminService.showTrendOfMonth(begin, end, type);
        return CommonResult.ok().data(map);
    }

}

