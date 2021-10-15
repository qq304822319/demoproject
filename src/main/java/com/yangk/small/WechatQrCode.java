package com.yangk.small;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangk
 * @date 2021/10/15
 */
public class WechatQrCode {


    //微信小程序access_token
    public static String token =
            "41_Nq_YZmvnISkXglPD_LMu_L5-Y7hhxmCWM32hYRKAmzMbjvBBVH_uFHcwhDJjA5vYEduRWpw-3X7_1PvhB8r8Ts2seOSpoDjEcNxjoaYr7u-ujL2H_XAfYRAlM6rqItgtfWGdEiG-JZpW2ZCOUYHgAJAEIR";


    static List<String> chids = new ArrayList<String>();

    //成功
    //@Test
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 30; i++) {
            chids.add("MKT-" + String.format("%06d", i+1));
            chids.add("RMR-" + String.format("%06d", i+1));

        }


        for (String chid : chids) {
            getQrCode(chid, 430);
            getQrCode(chid, 1280);
        }
    }

    private static void getQrCode(String chid, int side) throws IOException {
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("scene", chid);  //参数
        params1.put("page", "pages/bbdm/bbdm"); //位置
        params1.put("width", side);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = JSON.toJSONString(params1);
        //System.err.println(body);
        StringEntity entity;
        entity = new StringEntity(body);
        entity.setContentType("image/png");

        httpPost.setEntity(entity);
        HttpResponse response;

        response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        try {
            while ((ch = inputStream.read()) != -1) {
                bytestream.write(ch);
            }
        } catch (IOException e) {

        }
        byte[] program = bytestream.toByteArray();


        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("C:\\Users\\hhhh_\\Desktop\\QRcodeProd\\"+side+"\\" + chid + ".jpg"));
        imageOutput.write(program, 0, program.length);
        imageOutput.close();
    }

}
