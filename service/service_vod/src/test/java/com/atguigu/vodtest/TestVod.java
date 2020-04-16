package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

public class TestVod {

    //针对普通没有加密的视频，获取播放地址即可播放视频
    @Test
    public void testGetAddress() throws ClientException {

        //1、根据视频ID获取视频播放地址
        //创建初始化对象(参数为OSS存储图片的密钥和ID)
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FjPoGVuUZidKgQFbH6o", "FKiNwmaLxNxeTtkMobYroN2h0y9YLr");
        //2、创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        //3、向request对象里面设置视频id
        request.setVideoId("f600bec60bff466c928938d39147063d");

        //4、调用初始化对象里面的方法传递request，获取数据
        GetPlayInfoResponse response = client.getAcsResponse(request);

        //5、获取视频相关信息
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }

        //视频名称
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client,String videoId) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    //针对加密视频和不加密视频，获取播放凭证播放视频，需整合播放器
    @Test
    public void testGetVideoEvidence() throws Exception{
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FjPoGVuUZidKgQFbH6o", "FKiNwmaLxNxeTtkMobYroN2h0y9YLr");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        response = getVideoPlayAuth(client,"a50a6fa5689b4fd1b94a0bd0f725e8d5");
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }


    //根据视频ID获取视频播放凭证2
    @Test
    public void testGetVideoAuth() throws Exception{
        //根据视频ID获取播放凭证
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FjPoGVuUZidKgQFbH6o", "FKiNwmaLxNxeTtkMobYroN2h0y9YLr");
        //创建获取凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("f600bec60bff466c928938d39147063d");

        response = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());

    }

    /*********************************************************文件上传******************************************************************/

    /**
     * 获取视频上传地址和凭证
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取视频上传地址和凭证响应数据
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("1 - short video - upload by sdk");
        request.setFileName("E:\\1 - What If I Want to Move Faster.mp4");
        return client.getAcsResponse(request);
    }

    @Test
    public void testUplodByLocalFile(){
        DefaultAcsClient client = InitObject.initVodClient("LTAI4FjPoGVuUZidKgQFbH6o", "FKiNwmaLxNxeTtkMobYroN2h0y9YLr");
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        try {
            response = createUploadVideo(client);
            System.out.print("VideoId = " + response.getVideoId() + "\n");
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }


}
