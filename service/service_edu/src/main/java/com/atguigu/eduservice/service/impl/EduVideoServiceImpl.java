package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.microserver.VodService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodService vodService;

    //根据课程ID删除课程小节
    //TODO 删除小节的时候删除对应的视频文件
    @Override
    public void removeVideoByCourseId(String courseId){
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

    //删除单个阿里云视频文件
    public void removeAlyViodeFile(String eduVideoId){
        EduVideo eduVideo = this.getById(eduVideoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        vodService.removeAlyVideo(videoSourceId);
    }

    //删除课程时删除阿里云中的所有视频文件
    public void removeMoreAlyViodeFiles(String courseId){
        List<String> videoSourceIds = baseMapper.selectVideoSourceIdsByCourseId(courseId);
        vodService.removeMoreAlyVideos(videoSourceIds);
    }
}
