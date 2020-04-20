package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MsmService msmService;

    //发送短信注册码的接口
    @GetMapping("send/{phone}")
    public StandardResult sendMsm(@PathVariable String phone){
        //从redis获取验证码，如果获取不到再发送
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return StandardResult.ok();
        }
        //生成随机值，传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service中调用短信发送的方法
        boolean isSend = msmService.send(param,phone);
        if(isSend){
            //将验证码存到redis中并设置有效时间为5秒
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return StandardResult.ok();
        }else {
            return StandardResult.error().message("短信发送失败");
        }
    }
}
