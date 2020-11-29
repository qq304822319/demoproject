package com.yangk.demoproject;

import com.yangk.demoproject.common.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoprojectApplicationTests {
    @Autowired
    private TokenUtils tokenUtils;

    @Test
    void jsonWebTokenTest() {

        boolean b = tokenUtils.validateToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiZGV2ZXIiLCJuYW1lIjoieWFuZ2siLCJleHAiOjE2MDY2NDQ3MDB9.DfG5DBkJir2b5voKRhZiTaWl6NPh6kXsr1PnqiKTJu12CSpVswTShuPZy2v5ySBB8P7fCbJihn5uXGoTP92nSA");
        System.out.println("validateToken = " + b);
        Claims claims = tokenUtils.parseToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiZGV2ZXIiLCJuYW1lIjoieWFuZ2siLCJleHAiOjE2MDY2NDQ3MDB9.DfG5DBkJir2b5voKRhZiTaWl6NPh6kXsr1PnqiKTJu12CSpVswTShuPZy2v5ySBB8P7fCbJihn5uXGoTP92nSA");
        System.out.println(claims);
    }

}
