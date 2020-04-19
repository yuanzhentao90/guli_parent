package com.atguigu.educms.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页banner表 后台管理员对bannner图控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/educms/banner/admin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    //banner分页查询
    @GetMapping("/pageBanner/{current}/{limit}")
    public StandardResult pageBanner(@PathVariable long current,@PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(current,limit);
        bannerService.page(pageBanner,null);
        return StandardResult.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //添加banner
    @PostMapping("/addBanner")
    public StandardResult addBanner(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return StandardResult.ok();
    }

    //修改banner时的根据id查询banner
    @GetMapping("/get/{id}")
    public StandardResult getBannerById(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return StandardResult.ok().data("item",banner);
    }

    @PutMapping("/update")
    public StandardResult updataById(@RequestBody CrmBanner banner){
        bannerService.save(banner);
        return StandardResult.ok();
    }

    @DeleteMapping("/remove/{id}")
    public StandardResult remove(@PathVariable String id){
        bannerService.removeById(id);
        return StandardResult.ok();
    }
}

