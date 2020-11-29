package com.yangk.demoproject;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author yangk
 * @date 2020/11/22
 */
public class EncryptTests {

    public static void MD5Encrypt(){
        // 假设密码为123456,
        String password = "123456";
        // 传输前将密码通过MD5 产生信息摘要（Message-Digest）以防止被篡改，并非加密
        String md5Hex = DigestUtils.md5Hex(password);

        // 获取用户明文密码后MD5摘要
        if (DigestUtils.md5Hex("123456").equals(md5Hex)) {
            System.out.println("密码正确");
        }
    }


    public static void main(String[] args) {
        MD5Encrypt();
    }
}
