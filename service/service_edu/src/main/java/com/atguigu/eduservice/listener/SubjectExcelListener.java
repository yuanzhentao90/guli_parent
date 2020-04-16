package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    /**
     * SubjectExcelListener不能交给spring进行管理，所以不能注入其他对象
     * 只能将要用的EduSubjectService作为参数传进来
     *
     */
    private EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {
    }

    //读取Excel中的数据
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }

        //一行一行读取，每次读取有两个值，第一个值是一级分类，第二个值是二级分类
        //判断一级分类是否重复
        EduSubject oneSubject = this.exitOneSubject(subjectService, subjectData.getOneSubjectName());
        if(oneSubject == null){//表示excel表里面没有相同的一级分类
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }

        //添加二级分类
        //判断二级分类是否重复
        String pid = oneSubject.getId();
        EduSubject twoSubject = this.exitTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(twoSubject == null){//表示excel表里面没有相同的二级分类
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(twoSubject);
        }
    }

    //查询数据库中所有的一级分类，用以判断一级分类是否存在且不能重复添加(一级分类：parent_id=0)
    public EduSubject exitOneSubject(EduSubjectService subjectService , String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }


    //根据parent_id查询数据库中所有的二级分类，用以判断二级分类是否存在且不能重复添加
    public EduSubject exitTwoSubject(EduSubjectService subjectService , String name , String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
