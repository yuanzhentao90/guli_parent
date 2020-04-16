package com.atguigu.oss.comtroller;

import com.atguigu.commonutils.StandardResult;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public StandardResult uploadOssFile(MultipartFile file){

        String url = ossService.uploadFileAvatar(file);
        return StandardResult.ok().data("url",url);
    }
}
