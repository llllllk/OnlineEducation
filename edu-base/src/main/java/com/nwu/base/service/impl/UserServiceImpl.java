package com.nwu.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nwu.base.client.CloudClient;
import com.nwu.base.entity.User;
import com.nwu.base.mapper.UserMapper;
import com.nwu.base.service.UserService;
import com.nwu.base.vo.RegisterVo;
import com.nwu.common.exception.MyException;
import com.nwu.common.utils.MD5Util;
import com.nwu.common.vo.LoginInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-10-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private CloudClient cloudClient;

    @Override
    public LoginInfoVo getLoginInfo(Long userId) {
        User member = baseMapper.selectById(userId);
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        BeanUtils.copyProperties(member, loginInfoVo);
        return loginInfoVo;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String name = registerVo.getName();
        String phone = registerVo.getPhone();
        String sid = registerVo.getSid();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //校验参数
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) ||
                StringUtils.isBlank(name) || StringUtils.isBlank(sid)) {
            throw new MyException(444,"error");
        }
        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = (String) cloudClient.redisGetStr(phone).getData().get("data");
        if(!code.equals(mobleCode)) {
            throw new MyException(444,"验证码错误");
        }
        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<User>().eq("phone", phone));
        if(count > 0) {
            throw new MyException(444,"手机号已注册");
        }
        //添加注册信息到数据库
        User member = new User();
        member.setName(name);
        member.setPhone(registerVo.getPhone());
        member.setPassword(MD5Util.encrypt(password));
        member.setSid(sid);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }
}
