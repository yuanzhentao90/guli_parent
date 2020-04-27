package com.atguigu.orderservice.service.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.orderservice.Utils.OrderNoUtil;
import com.atguigu.orderservice.client.EduClient;
import com.atguigu.orderservice.client.UcentClient;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.mapper.OrderMapper;
import com.atguigu.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcentClient ucentClient;
    //生成订单
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucentClient.getUserInfoOrder(memberId);
        //通过远程调用根据课程id获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);//课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);//支付状态 订单状态（0：未支付 1：已支付）
        order.setPayType(1);//支付类型

        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
