package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
public interface OrderService extends IService<Order> {

    //生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);
}
