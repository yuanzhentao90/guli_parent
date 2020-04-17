package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;


    //课程信息条件查询带分页
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public StandardResult pageCourseCondition(@PathVariable long current,
                                              @PathVariable long limit,
                                              @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current,limit);
        eduCourseService.pageQuery(page,courseQuery);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        return StandardResult.ok().data("total",total).data("items",records);
    }


    //添加课程基本信息
    @PostMapping("/addCourseInfo")
    public StandardResult addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        //返回添加之后课程Id，为后面添加大纲使用
        return StandardResult.ok().data("courseId",courseId);
    }

    //根据课程查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public StandardResult getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return  StandardResult.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public StandardResult updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return StandardResult.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public StandardResult getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return StandardResult.ok().data("publishCourse",coursePublishVo);
    }

    //发布课程
    @PostMapping("/publishCourse/{id}")
    public StandardResult publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return StandardResult.ok();
    }

    //根据课程Id删除课程信息
    @DeleteMapping("/deleteCourse/{courseId}")
    public StandardResult deleteCourse(@PathVariable String courseId){
        eduCourseService.deleteCourseById(courseId);
        return StandardResult.ok();
    }
}

