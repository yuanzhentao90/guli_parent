package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码，参数是订单号
    @GetMapping("/createNative/{orderNo}")
    private StandardResult createNative(@PathVariable String orderNo){
        Map<String,Object> map = payLogService.createNative(orderNo);
        return StandardResult.ok().data(map);
    }

    //根据订单号查询订单支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public StandardResult queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return StandardResult.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")){
            //添加记录到支付表，更新订单表订单状态
            payLogService.updataOrderStatus(map);
            return StandardResult.ok().message("支付成功");
        }
        return StandardResult.ok().code(25000).message("支付中");
    }
}

