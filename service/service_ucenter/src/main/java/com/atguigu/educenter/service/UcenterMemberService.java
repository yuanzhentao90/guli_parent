package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-20
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录方法实现单点登录
    String login(UcenterMember member);

    //用户注册方法
    void register(RegisterVo registerVo);
}
