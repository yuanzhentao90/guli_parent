package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

//    @Autowired
//    private EduSubjectMapper subjectMapper;
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getOneTwoSubject() {

        //查出所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);

        //查出所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        for (EduSubject eduOneSubject:oneSubjects){
            OneSubject oneSubject = new OneSubject();
            //将前面对象的值，放到后面对象中去
            BeanUtils.copyProperties(eduOneSubject,oneSubject);
//            oneSubject.setId(eduOneSubject.getId());
//            oneSubject.setTitle(eduOneSubject.getTitle());
            List<TwoSubject> twoSubjectList = new ArrayList<>();

            for(EduSubject eduTwoSubject: twoSubjects){
                if(eduOneSubject.getId().equals(eduTwoSubject.getParentId())) {
                    //封装二级分类
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduTwoSubject,twoSubject);
//                    twoSubject.setId(eduTwoSubject.getId());
//                    twoSubject.setTitle(eduTwoSubject.getTitle());
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
            finalSubjectList.add(oneSubject);
        }
        return finalSubjectList;
    }
}