package com.atguigu.vod.controller;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadVideo")
    public StandardResult uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return StandardResult.ok().data("videoId",videoId);
    }

    @DeleteMapping("/removeVideo/{id}")
    public StandardResult deleteVideo(@PathVariable String id){
        String msg = vodService.deleteVideoAly(id);
        return StandardResult.ok().data("msg",msg);
    }
}
