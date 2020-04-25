package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //客户端使用查询课程，条件查询带分页
    @PostMapping("/getCousePageList/{page}/{limit}")
    public StandardResult getCoursePage(@PathVariable long page, @PathVariable long limit,
                                        @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseFrontVo);
        return StandardResult.ok().data("courses",map);
    }

    @GetMapping("/getFrontCourseInfo/{courseId}")
    public StandardResult getFrontCourseInfo(@PathVariable String courseId){
        //1、根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCouseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return StandardResult.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }
}
