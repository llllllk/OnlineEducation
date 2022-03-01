package com.nwu.cloud.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class VodUtil {
    public static DefaultAcsClient getClient(){
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ConstantUtil.ACCESS_KEY_ID, ConstantUtil.ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }
}
