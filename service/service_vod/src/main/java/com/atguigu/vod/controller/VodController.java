package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.StandardResult;
import com.atguigu.servicebase.exceptionHandler.GuliException;
import com.atguigu.vod.Utils.ConstantVodUtils;
import com.atguigu.vod.Utils.InitVodClient;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    //整合阿里云视频播放器，获取视频凭证
    @GetMapping("/getVideoAuth/{id}")
    public StandardResult getVideoAuth(@PathVariable String id){

        try {
            //1、获取client
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            //获取播放凭证并返回
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return StandardResult.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取播放凭证失败");
        }

    }
}
