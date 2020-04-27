package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-27
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public List<EduComment> pageCommentByCourseId(Page<EduComment> commentPage, String courseId) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        return null;
    }
}
