package com.qiu.paper_management.service;

import com.qiu.paper_management.pojo.User;

import java.util.Map;

public interface UserService {
    User findByUsername(String username);

    void register(String username, String password) throws Exception;

    void update(User user);

    void updateImg(String imgAddr);

    void updatePwd(String newPwd);
}
