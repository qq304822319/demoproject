package com.yangk.demoproject;

import com.yangk.demoproject.common.utils.HttpUtils.HttpClientUtils;
import com.yangk.demoproject.common.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangk
 * @date 2020/11/22
 */
@SpringBootTest
public class UtilsTsets {

    /*
     * TEST：请求SSL(TLSv1.2)接口
     *
     * @param
     * @return void
     * @author yangk
     * @date 2020/11/22
     */
    @Test
    void callSSLrestfulAPI() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {


        String url = "接口url";

        //实例化RestTemplate 访问 restful接口
        RestTemplate template = new RestTemplate(HttpClientUtils.requestSSLFactoryNoProxy());

        // 设置request header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        // 设置request body
        String requestBodyString = "{\"name\":\"杨凯\"}";

        // 访问接口
        HttpEntity<Object> httpEntity = new HttpEntity<>((Object) requestBodyString, httpHeaders);

        // 返回结果
        ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);

        // 获取responseBody
        String responseBodyString = responseEntity.getBody();
    }


    /*
     * TokenUtilsTest
     *
     * @author yangk
     * @date 2020/11/29
     */
    @Autowired
    private TokenUtils tokenUtils;
    @Test
    void jsonWebTokenTest() {
        //自定义token含义
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("name", "yangk");
        conditions.put("role", "dever");

        //生成有期限的token
        String token = tokenUtils.createToken(conditions);
        System.out.println(token);

        /*************/
        //判断token是否过期
        boolean b = tokenUtils.validateToken(token);
        System.out.println("validateToken = " + b);
        //解析token，过期依然能解析出含义
        Claims claims = tokenUtils.parseToken(token);
        System.out.println(claims);
    }
}
