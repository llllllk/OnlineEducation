package com.nwu.statistic.schedule;

import com.nwu.common.utils.DateUtil;
import com.nwu.statistic.service.VisualAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Task {

    @Autowired
    private VisualAdminService adminService;
    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.getOtherDays(new Date(), -1));
        adminService.createData(day);
        System.out.println("*********++++++++++++*****执行了");
    }
}
