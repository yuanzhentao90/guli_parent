package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.StandardResult;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //1、生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public StandardResult saveOrder(@PathVariable String courseId , HttpServletRequest request){
        //获取用户ID
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单、返回订单号
        String orderNo = orderService.createOrders(courseId,memberIdByJwtToken);
        return StandardResult.ok().data("orderNo",orderNo);
    }

    //2、根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public StandardResult getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",orderId);
        Order order = orderService.getOne(queryWrapper);
        return StandardResult.ok().data("order",order);
    }
}

