package com.qiu.paper_management.controller;

import com.qiu.paper_management.pojo.Result;
import com.qiu.paper_management.pojo.User;
import com.qiu.paper_management.service.UserService;
import com.qiu.paper_management.utils.JwtUtil;
import com.qiu.paper_management.utils.Md5Util;
import com.qiu.paper_management.utils.ThreadLocalUtil;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redis;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{2,30}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) throws Exception{
        // 1. 查询用户是否已经存在
        User user = userService.findByUsername(username);
        if(user != null)
            return Result.error("用户已注册，请直接登录");

        // 2. 如果不存在，新注册
        // 这里不需要try catch，因为有全局异常处理器
        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{2,30}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        // 1. 判断用户是否存在
        User user = userService.findByUsername(username);
        if(user == null)
            return Result.error("用户名不存在！");

        // 2. 如果存在，判断密码是否正确
        if(Md5Util.checkPassword(password, user.getPassword())){
            // 1. 生成 Jwt token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String data = JwtUtil.genToken(claims);

            // 2. 存入redis中
            ValueOperations<String, String> operations = redis.opsForValue();
            operations.set("token", data, JwtUtil.getExpireHour(), TimeUnit.HOURS);
            return Result.success(data);
        }
        return Result.error("用户名或密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo (){
        String username = ThreadLocalUtil.getUsername();
        User user = userService.findByUsername(username);
        return Result.success(user);
    }


    @PutMapping("/update")
    public Result userUpdate(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result imgUpdate(@RequestParam("avatarUrl") @URL String imgAddr){
        userService.updateImg(imgAddr);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result pwdUpdate(@RequestBody Map<String, String> params){
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get(("re_pwd"));

        // 1. 参数模式合法性
        // 已经封装了null值检验
        if(!StringUtils.hasLength(old_pwd) || !StringUtils.hasLength(new_pwd) || !StringUtils.hasLength(re_pwd))
            return Result.error("缺少必要的参数！");

        // 2. 参数逻辑合法性
        // 2.1 旧密码输入错误
        String username = ThreadLocalUtil.getUsername();
        User user = userService.findByUsername(username);
        String realPwd = user.getPassword();
        if (!realPwd.equals(Md5Util.getMD5String(old_pwd)))
            return Result.error("旧密码输入错误！");

        // 2.2 新旧密码必须不同
        if(old_pwd.equals(new_pwd))
            return Result.error("新密码必须与旧密码不同！");

        // 2.3 再次输入需和新密码相同
        if(!new_pwd.equals(re_pwd))
            return Result.error("再次输入有误！");

        userService.updatePwd(Md5Util.getMD5String(new_pwd));

        // 3. 使得原先的token失效，此时需要重新登录
//        跟视频不同，但是果然是删除键而不是值吧！
        ValueOperations<String, String> operations = redis.opsForValue();
        operations.getOperations().delete("token");
        return Result.success();
    }
}
