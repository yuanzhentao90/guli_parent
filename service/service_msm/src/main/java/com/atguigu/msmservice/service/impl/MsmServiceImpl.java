package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    //发送短信的方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone))
            return false;
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4GGqF9Guxdk23menv6WQ", "0uYIaBhR6R0LKnv2zmwbHdgjBU8ckN");
        //创建发送短信的对象
        IAcsClient client = new DefaultAcsClient(profile);
        //设置相关参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //设置发送相关参数
        request.putQueryParameter("PhoneNumbers", phone);//需要发送的手机号
        request.putQueryParameter("SignName", "Java学习用户注册功能");//设置申请的阿里云中的签名名称
        request.putQueryParameter("TemplateCode", "SMS_188555551");//设置申请的阿里云中的模板的code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//设置需要发送的验证码，需要转换成JSON
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            return commonResponse.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
