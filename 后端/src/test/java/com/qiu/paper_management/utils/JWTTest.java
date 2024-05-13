package com.qiu.paper_management.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {
    @Test
    public void generate(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "测试人员");
        claims.put("id", "1");
        String token = JWT.create()
                .withClaim("user", claims)// 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))// 过期时间
                .sign(Algorithm.HMAC256("publickey"));// 指定算法和密钥(分不清公玥私玥了，无所谓)

        System.out.println(token);
    }

    @Test
    public void verify(){
        String code = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoi5rWL6K-" +
                "V5Lq65ZGYIn0sImV4cCI6MTcxMzI1Njk1MX0.i-" +
                "PxljOVPVMANU7xj_tSJsmX_eCaQAVWst2JaLTf15U";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("publickey")).build();
        DecodedJWT text = jwtVerifier.verify(code);
        Map<String, Claim> claims = text.getClaims();
        System.out.println(claims.get("user"));

    }
}
