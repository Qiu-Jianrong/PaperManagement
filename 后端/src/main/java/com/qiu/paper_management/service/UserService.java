package com.qiu.paper_management.service;

import com.qiu.paper_management.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findByUsername(String username);

    void register(String username, String password) throws Exception;

    void update(User user);

    void updateImg(String imgAddr);

    void updatePwd(String newPwd);

    List<User> getAllUsers();

    String getImgById(Integer criticId);

    String getEmailById(Integer criticId);
}
