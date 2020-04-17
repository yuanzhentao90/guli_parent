package com.atguigu.eduservice.microserver;

import com.atguigu.commonutils.StandardResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("service-vod")
public interface VodService {

    //调用Vod项目中的方法，通过id删除阿里云中的视频
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public StandardResult removeAlyVideo(@PathVariable("id") String id);

    //当删除课程时删除课程中的所有保存到阿里云的视频
    @DeleteMapping("/eduvod/video/removeMoreAlyVideos")
    public StandardResult removeMoreAlyVideos(@RequestParam("videoIdList") List<String> videoIdList);
}
