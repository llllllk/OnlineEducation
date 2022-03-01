package com.nwu.common.vo;

import lombok.Data;

@Data
public class LoginInfoVo {
    private Long id;
    private String phone;
    private String sid;
    private String name;
    private Integer sex;
    private String avatar;
}
