package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VodService {
    String uploadVideoAly(MultipartFile file);

    String deleteVideoAly(String id);

    void removeMoreAlyVideos(List<String> videoIdList);
}
