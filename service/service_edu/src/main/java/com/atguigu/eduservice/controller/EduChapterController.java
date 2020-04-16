package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //课程大纲列表
    @GetMapping("/getChaperVideo/{courseId}")
    public StandardResult getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return StandardResult.ok().data("items",list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public StandardResult addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return StandardResult.ok();
    }

    //根据章节id查询
    @GetMapping("/getChapterInfo/{chapterId}")
    public StandardResult getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return StandardResult.ok().data("eduChapter",eduChapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public StandardResult updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return StandardResult.ok();
    }

    //删除章节（章节下有小节，不允许删除）
    @DeleteMapping("/deleteChapter/{chapterId}")
    public StandardResult deleteChapter(@PathVariable String chapterId){
        String result = eduChapterService.deleteChapter(chapterId);
        return StandardResult.ok().data("msg",result);
    }
}

