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

    //根据课程ID删除课程小节
    void removeVideoByCourseId(String courseId);
}
