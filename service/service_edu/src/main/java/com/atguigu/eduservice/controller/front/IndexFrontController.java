package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 显示前端首页中的banner和讲师
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;
    //1、查询前八条热门课程,查询前4名讲师
    @GetMapping("/index")
    public StandardResult index(){
        List<EduCourse> hotCourses = courseService.getHotCouse();
        List<EduTeacher> topTeachers = teacherService.getTopTeacher();
        return StandardResult.ok().data("courses",hotCourses).data("teachers",topTeachers);
    }


}
