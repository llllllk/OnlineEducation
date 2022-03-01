package com.nwu.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.nwu.cloud.service.MsgService;
import com.nwu.cloud.utils.ConstantUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsgServiceImpl implements MsgService {

    @Override
    public boolean send(String PhoneNumbers, Map<String, Object> param) {
        if(StringUtils.isEmpty(PhoneNumbers)) return false;
        DefaultProfile profile =
        DefaultProfile.getProfile("default", ConstantUtil.ACCESS_KEY_ID, ConstantUtil.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", ConstantUtil.SIGN_NAME);
        request.putQueryParameter("TemplateCode", ConstantUtil.TEMPLATE_CODE);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
