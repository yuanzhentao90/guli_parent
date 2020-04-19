package com.atguigu.educms.controller;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前台用户页面显示banner
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/educms/banner/front")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/getAllBanner")
    public StandardResult getAllBanner(){
        List<CrmBanner> list = bannerService.selectAllBanner();
        return StandardResult.ok().data("items",list);
    }
}
