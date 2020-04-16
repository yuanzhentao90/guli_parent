package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
public interface EduChapterService extends IService<EduChapter> {

    //根据课程ID查询出所有小节
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //根据课程ID删除章节
    String deleteChapter(String chapterId);

    //根据课程ID删除章节信息
    void removeChapterByCourseId(String courseId);
}
