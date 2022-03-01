package com.nwu.cloud.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String getVideoLocation(String id);
    String getVideoPlayAuth(String id);
    String uploadVideo(MultipartFile file);
    boolean removeVideo(String videoId);
    void removeVideoList(List<String> videoIdList);
}
