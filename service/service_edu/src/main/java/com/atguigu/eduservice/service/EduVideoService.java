package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程ID删除所有课程小节
    void removeVideoByCourseId(String courseId);

    //根据eduVideoId删除单个阿里云视频
    void removeAlyViodeFile(String eduVideoId);

    //删除课程时删除阿里云中的所有视频文件
    void removeMoreAlyViodeFiles(String courseId);
}
