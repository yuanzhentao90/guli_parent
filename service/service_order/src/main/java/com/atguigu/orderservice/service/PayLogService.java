package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
public interface PayLogService extends IService<PayLog> {


    Map<String, Object> createNative(String orderNo);

    //根据订单号查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //向支付表里面添加支付记录，更新订单表的状态
    void updataOrderStatus(Map<String, String> map);
}
