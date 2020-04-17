package com.atguigu.vod.controller;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传文件到案例云中
    @PostMapping("/uploadVideo")
    public StandardResult uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return StandardResult.ok().data("videoId",videoId);
    }

    //删除阿里云中的视频文件
    @DeleteMapping("/removeVideo/{id}")
    public StandardResult deleteVideo(@PathVariable String id){
        String msg = vodService.deleteVideoAly(id);
        return StandardResult.ok().data("msg",msg);
    }

    //删除课程时删除课程下的所有视频
    @DeleteMapping("/removeMoreAlyVideos")
    public StandardResult removeMoreAlyVideos(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAlyVideos(videoIdList);
        return StandardResult.ok();
    }
}
