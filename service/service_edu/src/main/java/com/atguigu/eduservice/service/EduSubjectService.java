package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-13
 */
public interface EduSubjectService extends IService<EduSubject> {

    /*
    讲excel表格中的课程数据导入到数据
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /*
    获取所有一级二级分类数据，一级分类中包含二级分类
     */
    List<OneSubject> getOneTwoSubject();
}
