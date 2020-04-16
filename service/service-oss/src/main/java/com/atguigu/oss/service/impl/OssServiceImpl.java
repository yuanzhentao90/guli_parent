package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantYmlUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantYmlUtil.END_POINT;
        String accessKeyId = ConstantYmlUtil.KEY_ID;
        String accessKeySecret = ConstantYmlUtil.KEY_SECRET;
        String bucketName = ConstantYmlUtil.BUCKET_NAME;


        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取文件上传输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //1.在文件名称里面添加一个随机的值(为避免重复文件名，后上传的文件覆盖先上传的文件)
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //2.把文件按照日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");

            fileName = datePath+"/"+fileName;

            // 调用上传方法
            //参数是说明：1、bucketName,2、上传到OSS的文件路径和文件名称 /a/1.jpg，3、上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;

            // 关闭OSSClient。
            ossClient.shutdown();
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
