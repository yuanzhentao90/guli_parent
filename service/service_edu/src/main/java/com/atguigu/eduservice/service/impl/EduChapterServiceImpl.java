package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1、根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> chapter = new QueryWrapper<>();
        chapter.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapter);
        //2、根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> video = new QueryWrapper<>();
        video.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(video);
        //最后返回的章节
        List<ChapterVo> resultList = new ArrayList<>();
        //3、遍历查询出来的章节list集合进行封装
        for(EduChapter eduChapter:eduChapters){
            ChapterVo chapterVo = new ChapterVo();
            //eduChapter对象中的值复制到chapterVo中去
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //将chapterVo放到返回的resultList中去
            resultList.add(chapterVo);
            //章节中的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //4、遍历查询出来的小节list结合进行封装
            for (EduVideo eduVideo:eduVideos){
                VideoVo videoVo = new VideoVo();
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return resultList;
    }

    @Override
    public String deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        String msg = null;
        if (count>0){
            throw new GuliException(20001,"请先删除章节下的小节");
        }else {
            int i = baseMapper.deleteById(chapterId);
            msg = "删除了"+i+"条章节";
        }
        return msg;
    }

    //根据课程ID删除课程章节信息
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
