package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-19
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //前台页面显示banner，查询出来之后放到redis中
    List<CrmBanner> selectAllBanner();
}
