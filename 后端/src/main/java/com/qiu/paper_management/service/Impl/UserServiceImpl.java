package com.qiu.paper_management.service.Impl;

import com.qiu.paper_management.mapper.UserMapper;
import com.qiu.paper_management.pojo.User;
import com.qiu.paper_management.service.UserService;
import com.qiu.paper_management.utils.Md5Util;
import com.qiu.paper_management.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void register(String username, String password) throws Exception{
        // 1. 加密(这步没见过)
        String password_encoded = Md5Util.getMD5String(password);
        // 2. 注册
        userMapper.register(username, password_encoded);
//        throw new Exception("试一试service层抛出的异常");

    }

    @Override
    public void update(User user) {
        // 需要记得更新时间要setter一下
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateImg(String imgAddr) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updateImg(imgAddr, id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updatePwd(newPwd, id);
    }
}
