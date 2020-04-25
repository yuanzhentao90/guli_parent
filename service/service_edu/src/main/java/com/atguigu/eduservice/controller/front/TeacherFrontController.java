package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //客户端显示讲师信息
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public StandardResult getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        return StandardResult.ok().data("map",map);
    }

    //讲师详情
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public StandardResult getTeacherFrontInfo(@PathVariable String teacherId){
        //1、查询讲师的基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2、根据讲师ID查询所讲课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> courses = courseService.list(queryWrapper);
        return StandardResult.ok().data("eduTeacher",eduTeacher).data("courses",courses);
    }
}
