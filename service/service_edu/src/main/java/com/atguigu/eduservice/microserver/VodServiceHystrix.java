package com.atguigu.eduservice.microserver;

import com.atguigu.commonutils.StandardResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodServiceHystrix implements VodService{
    @Override
    public StandardResult removeAlyVideo(String id) {
        return StandardResult.error().message("服务提供者出错");
    }

    @Override
    public StandardResult removeMoreAlyVideos(List<String> videoIdList) {
        return StandardResult.error().message("服务提供者出错");
    }
}
