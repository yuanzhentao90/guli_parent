package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //先将文件提价到服务器，再进行读取操作
    @PostMapping("addSubject")
    public StandardResult addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return StandardResult.ok();
    }

    //课程分类的列表功能
    @GetMapping("getAllSubject")
    public StandardResult getAllSubject(){
        List<OneSubject> list = subjectService.getOneTwoSubject();
        return StandardResult.ok().data("list",list);
    }
}

