package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    //课程评论分页
    @GetMapping("/pageComment/{courseId}/{current}/{limit}")
    public StandardResult pageComment(@PathVariable String courseId,
                                      @PathVariable long current,
                                      @PathVariable long limit){
        Page<EduComment> commentPage = new Page<>(current,limit);
        List<EduComment> commentList = commentService.pageCommentByCourseId(commentPage,courseId);
        return StandardResult.ok();
    }


}

