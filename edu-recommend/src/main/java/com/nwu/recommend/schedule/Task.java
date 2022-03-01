package com.nwu.recommend.schedule;

import com.nwu.recommend.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class Task {

    @Autowired
    private RecommendService recommendService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void task1() {
        //获取上一天的日期
        recommendService.getSimilarity();
        log.info("执行相似度计算");
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void task2() {
        //获取上一天的日期
        recommendService.getUserResult();
        log.info("基于用户推荐");
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void task3() {
        //获取上一天的日期
        recommendService.getItemResult();
        log.info("基于项目推荐");
    }
}
