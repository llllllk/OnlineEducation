package com.nwu.cloud.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.nwu.cloud.service.VodService;
import com.nwu.cloud.utils.ConstantUtil;
import com.nwu.cloud.utils.VodUtil;
import com.nwu.common.exception.MyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    //获取视频播放地址
    public String getVideoLocation(String id){
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = VodUtil.getClient();
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        String location=null;
        try {
            //注意：这里只能获取非加密视频的播放地址
            request.setVideoId(id);
            GetPlayInfoResponse response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            location = playInfoList.get(0).getPlayURL();
        } catch (Exception e) {
            throw new MyException(444, "获取地址失败");
        }
        return location;
    }

    @Override
    public String getVideoPlayAuth(String id) {
        DefaultAcsClient client = VodUtil.getClient();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        String auth=null;
        try {
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            auth = response.getPlayAuth();
        } catch (Exception e) {
            throw new MyException(444, "视频视频凭证失败");
        }
        return auth;
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantUtil.ACCESS_KEY_ID,
                    ConstantUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new MyException(444, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new MyException(444, "视频服务上传失败");
        }
    }

    @Override
    public boolean removeVideo(String videoId) {
        try{
            DefaultAcsClient client = VodUtil.getClient();
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            return true;
        }catch (ClientException e){
            throw new MyException(444, "视频删除失败");
        }
    }

    @Override
    public void removeVideoList(List<String> videoIdList) {
        try {
            DefaultAcsClient client = VodUtil.getClient();
            //一次只能批量删20个
            String str = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(str);
            //获取响应
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new MyException(444, "批量视频删除失败");
        }
    }
}
