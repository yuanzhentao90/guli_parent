package com.atguigu.staservice.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-29
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    //统计某一天注册人数，并加到数据库中
    @PostMapping("/registerCount/{day}")
    public StandardResult registerCount(@PathVariable String day){
        staService.registerCount(day);
        return StandardResult.ok();
    }

    //获取ECharts显示数据
    @GetMapping("/getShowData/{type}/{begin}/{end}")
    public StandardResult getShowData(@PathVariable String type,
                                      @PathVariable String begin,
                                      @PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return StandardResult.ok().data(map);
    }
}

