package com.qiu.paper_management.mapper;

import com.qiu.paper_management.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from paper_management.user where username=#{username};")
    User findByUsername(String username);

    // 注册时无需昵称，也行吧。"Id自动生成"这个没理解
    // now()函数数据库内置，获取数据库时间
    @Insert("insert into paper_management.user(username, password, create_time, update_time) " +
            "values(#{username}, #{password}, now(), now());")
    void register(String username, String password) throws Exception;

    @Update("update paper_management.user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update paper_management.user set user_pic=#{imgAddr}, update_time=now() where id=#{id}")
    void updateImg(String imgAddr, Integer id);

    @Update("update paper_management.user set password=#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(String newPwd, Integer id);

    @Select("select * from paper_management.user")
    List<User> getAllUsers();

    @Select("select user_pic from paper_management.user where id=#{criticId}")
    String getImgById(Integer criticId);

    @Select("select email from paper_management.user where id=#{criticId}")
    String getEmailById(Integer criticId);
}
