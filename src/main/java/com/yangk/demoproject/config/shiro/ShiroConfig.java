package com.yangk.demoproject.config.shiro;

/**
 * shiro配置
 *
 * @author yangk
 */
public class ShiroConfig {
    public static final String TOKEN = "token";

    /**
     * 加密的次数
     */
    public static final Integer HASH_ITERATIONS = 15;

    /**
     * 加密方式
     */
    public static final String HASH_ALGORITHM_NAME = "MD5";
}
