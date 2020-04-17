package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.StandardResult;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author YuanZT
 * @since 2020-04-14
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //1、添加小节
    @PostMapping("/addEduVideo")
    public StandardResult addEduVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return StandardResult.ok();
    }
    //2、删除小节
    //TODO 后面删除小节的时候，同时删除里面的视频
    @DeleteMapping("/deleteEduVideo/{eduVideoId}")
    public StandardResult deleteEduVideo(@PathVariable String eduVideoId){
        eduVideoService.removeAlyViodeFile(eduVideoId);
        eduVideoService.removeById(eduVideoId);
        return StandardResult.ok();
    }

    //3、修改小节
    //3-1根据id查询小节
    @GetMapping("/getVideoById/{videoId}")
    public StandardResult getVideoById(@PathVariable String videoId){
        EduVideo video = eduVideoService.getById(videoId);
        return StandardResult.ok().data("eduVideo",video);
    }

    //3-2修改小节
    @PostMapping("/updateVideo")
    public StandardResult updateVideo(@RequestBody EduVideo eduVideo){
        System.out.println("eduVideo = " + eduVideo);
        eduVideoService.updateById(eduVideo);
        return StandardResult.ok();
    }

}

