package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionHandler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxLoginController {

    @Autowired
    private UcenterMemberService memberService;

    //1.生成微信二维码
    @RequestMapping("/login")
    public String getWxCode(){
        //声明基础路径，%s相当于占位符
        String baseuUrl = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEcoder编码，redirect_url为扫码登录之后要去的地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;

        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //向基础路劲中注入值，并返回
        String url = String.format(baseuUrl,
                ConstantWxUtils.WX_OPEN_APP_id,
                redirectUrl,
                "atguigu"
        );
        //重定向到请求微信地址里面
        return "redirect:"+url;
    }


    //2、获取扫码账号信息，添加数据
    @GetMapping("/callback")
    public String callback(String code,String state){

        try {
            //3、拿到code值，请求微信提供的固定地址，获取到access_token（访问凭证）和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl
                    , ConstantWxUtils.WX_OPEN_APP_id
                    , ConstantWxUtils.WX_OPEN_APP_SECRET
                    , code);
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //使用gson解析accessTokenInfo
            Gson gson = new Gson();
            HashMap<String,String> mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = mapAccessToken.get("access_token");
            String openid = mapAccessToken.get("openid");


            UcenterMember member = memberService.getMemberByOpenId(openid);
            if(member == null){
                //4、使用HttpClient拿着access_token和openid再去请求微信提供的固定地址，最终获取微信扫描人的信息。
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl,
                        access_token,
                        openid);
                //获取到的扫码用户信息
                String userInfo = HttpClientUtils.get(userInfoUrl);
//            System.out.println(userInfo);
                //解析用户信息
                HashMap<String, String> userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = userInfoMap.get("nickname");//昵称
                String headimgurl = userInfoMap.get("headimgurl").replace("\\","");//头像
//            System.out.println(headimgurl);
//            String sex = (userInfoMap.get("sex")-1).toString();//1表示男性，2表示女性
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setOpenid(openid);
                memberService.save(member);
            }
            //使用JWT根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000/?token="+jwtToken;//跳转到页面
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(2001,"登录失败");
        }

    }
}
