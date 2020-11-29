package com.yangk.demoproject;

import com.yangk.demoproject.common.utils.HttpUtils.HttpClientUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author yangk
 * @date 2020/11/22
 */
@SpringBootTest
public class CallHttpAPITsets {

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

}
