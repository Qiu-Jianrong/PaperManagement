package com.qiu.paper_management.interceptors;

import com.qiu.paper_management.utils.JwtUtil;
import com.qiu.paper_management.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redis;
    // 该拦截器用于检验是否登录，方式是JWT令牌检验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 前端新增需求
        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String token = request.getHeader("Authorization");

        try{
            // 1. 只要redis中没有token就说明用户不在登录状态，那这个token就是伪造的
            ValueOperations<String, String> operations = redis.opsForValue();
            if(operations.get("token") == null)
                throw new RuntimeException();
            // id + username
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // 2. 使用ThreadLocal来避免接下来该用户的业务中重复解析，它是线程安全的
            ThreadLocalUtil.set(claims);
        }
        catch (Exception e){
            response.setStatus(401);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空threatlocal数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
