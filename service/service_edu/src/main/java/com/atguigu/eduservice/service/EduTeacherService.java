package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-07
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //获取4位名师，放在客户端页面显示
    List<EduTeacher> getTopTeacher();

    //前端获取讲师列表的方法
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
