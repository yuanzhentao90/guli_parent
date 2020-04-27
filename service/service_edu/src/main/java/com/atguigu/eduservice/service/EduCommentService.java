package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
public interface EduCommentService extends IService<EduComment> {

    //根据课程id获取课程评论
    List<EduComment> pageCommentByCourseId(Page<EduComment> commentPage, String courseId);
}
