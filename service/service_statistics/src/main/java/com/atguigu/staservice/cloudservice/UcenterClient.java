package com.atguigu.staservice.cloudservice;

import com.atguigu.commonutils.StandardResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //统计每天注册人数
    @GetMapping("/educenter/member/dayRegister/{day}")
    public StandardResult dayRegister(@PathVariable("day") String day);
}
