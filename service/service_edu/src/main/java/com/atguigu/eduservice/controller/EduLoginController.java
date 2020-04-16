package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.StandardResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {

    //login
    @PostMapping("/login")
    public StandardResult login(){

        return StandardResult.ok().data("token","admin");
    }

    //info
    @GetMapping("/info")
    public StandardResult info(){

        return StandardResult.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
