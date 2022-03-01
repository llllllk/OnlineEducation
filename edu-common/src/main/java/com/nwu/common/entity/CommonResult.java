package com.nwu.common.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CommonResult {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<>();
    private CommonResult(){}
    public static CommonResult ok(){
        CommonResult R = new CommonResult();
        R.code=666;R.success=true;R.message="请求成功";
        return R;
    }
    public static CommonResult error(){
        CommonResult R = new CommonResult();
        R.code=444;R.success=false;R.message="请求失败";
        return R;
    }
    public CommonResult message(String msg){
        this.setMessage(msg);
        return this;
    }
    public CommonResult data(String key,Object data){
        this.getData().put(key,data);
        return this;
    }
    public CommonResult data(Map<String,Object> data){
        this.setData(data);
        return this;
    }
    public CommonResult code(Integer code){
        this.setCode(code);
        return this;
    }
}
