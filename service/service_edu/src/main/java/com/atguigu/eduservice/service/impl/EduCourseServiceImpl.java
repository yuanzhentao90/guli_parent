package com.atguigu.eduservice.service.impl;


import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表中添加课程基本信息
        //把courseInfoVo对象转成EduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        //返回受影响行数
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            throw new GuliException(20001,"添加课程信息失败！");
        }

        //获取course的ID
        String cid = eduCourse.getId();

        //向课程简介表中添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置课程描述Id就是课程ID
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return  cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1、修改课程表edu_course
        EduCourse educourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,educourse);
        int update = baseMapper.updateById(educourse);
        if(update == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }

        //2、修改描述表edu_course_description
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
//        eduCourseDescription.setId(educourse.getId());
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void pageQuery(Page<EduCourse> page, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("gmt_create");
        if(courseQuery == null){
            baseMapper.selectPage(page,queryWrapper);
        }

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            queryWrapper.eq("status",status);
        }

        baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public void deleteCourseById(String courseId) {

        //1、删除小节信息
        //1-1、删除课程所有小节中的视频文件（阿里云平台中）
        eduVideoService.removeMoreAlyViodeFiles(courseId);
        eduVideoService.removeVideoByCourseId(courseId);
        //2、删除章节信息
        eduChapterService.removeChapterByCourseId(courseId);
        //3、删除描述信息
        eduCourseDescriptionService.removeById(courseId);
        //4、删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0){
            throw new GuliException(20001,"删除失败");
        }
    }

    //查询热门课程，将数据放到redis缓存中，放在客户端页面中使用。
    @Override
    @Cacheable(key="'hotCoureList'" , value = "hotCourses")
    public List<EduCourse> getHotCouse() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count");
        queryWrapper.last("limit 8");
        List<EduCourse> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    //客户端条件查询带分页
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            queryWrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }

        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            queryWrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            queryWrapper.orderByDesc("buy_count");
        }

        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            queryWrapper.orderByDesc("gmt_create");
        }

        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage,queryWrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String,Object> map = new HashMap<>();
        map.put("records",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCouseInfo(String courseId) {
        return baseMapper.getBaseCouseInfo(courseId);
    }

}
