package com.nwu.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nwu.base.entity.User;
import com.nwu.base.vo.RegisterVo;
import com.nwu.common.vo.LoginInfoVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lk
 * @since 2021-10-17
 */
public interface UserService extends IService<User> {

    LoginInfoVo getLoginInfo(Long userId);

    void register(RegisterVo registerVo);
}
