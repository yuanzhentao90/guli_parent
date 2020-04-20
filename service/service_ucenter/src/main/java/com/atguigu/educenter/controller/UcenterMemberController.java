package com.atguigu.educenter.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-20
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //1、登录
    @PostMapping("/login/")
    public StandardResult userLogin(@RequestBody UcenterMember member){
        //返回值token，使用JWT生成token字符串
        String token = memberService.login(member);
        return StandardResult.ok().data("token",token);
    }

    //2、注册

}

