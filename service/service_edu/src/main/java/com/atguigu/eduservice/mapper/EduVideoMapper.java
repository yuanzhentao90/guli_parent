package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
public interface EduVideoMapper extends BaseMapper<EduVideo> {

    //根据课程ID courseId查询到edu_video表中的所有video_source_id,删除课程时删除课程中的所有视频文件
    public List<String> selectVideoSourceIdsByCourseId(String courseId);

}
