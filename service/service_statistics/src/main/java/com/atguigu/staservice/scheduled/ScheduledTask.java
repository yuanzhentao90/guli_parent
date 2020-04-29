package com.atguigu.staservice.scheduled;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;
    //每天凌晨一点执行方法进行数据添加
    //前一天的数据进行查询并添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        Date date = DateUtil.addDays(new Date(), -1);
        staService.registerCount(DateUtil.formatDate(date));
    }
}
