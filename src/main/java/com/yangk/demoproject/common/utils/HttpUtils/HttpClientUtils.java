package com.yangk.demoproject.common.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * @author yangk
 * @date 2020/11/22
 */
@Slf4j
public class HttpClientUtils {

    private static PoolingHttpClientConnectionManager connectionManager;

    private static Properties properties;

    //代理地址
    private static String PROXYHOST;
    //代理端口
    private static int PROXYPORT;
    //超时
    private static int TIMEOUT;

    private static int MAX_TOTAL_CONNECTIONS;
    private static int MAX_ROUTE_CONNECTIONS;

    //初始化参数
    static {
        InputStream in = null;

        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.yml");
            properties = new Properties();
            properties.load(in);
        } catch (Exception e) {
            log.info("HttpClientUtils.static ResourceAsStream error: {}", e.getMessage());
        } finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    log.info("HttpClientUtils.static close InputStream error: {}", e.getMessage());
                }
            }
        }


        PROXYHOST = properties.getProperty("");
        PROXYPORT = Integer.parseInt(properties.getProperty(""));
        TIMEOUT = Integer.parseInt(properties.getProperty(""));
    }

    /*
     * 无代理创建SSLHttpRequset工厂
     *
     * @param
     * @return org.springframework.http.client.HttpComponentsClientHttpRequestFactory
     * @author yangk
     * @date 2020/11/22
     */
    public static HttpComponentsClientHttpRequestFactory requestSSLFactoryNoProxy()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        TrustStrategy trustStrategy = (certificate, type) -> true;

        SSLContext sslContext = org.apache.http.conn.ssl.SSLContexts.custom().
                useProtocol("TLSv1.2").loadTrustMaterial(null, trustStrategy).build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());

        requestFactory.setHttpClient(httpClient);

        requestFactory.setConnectTimeout(TIMEOUT);

        return requestFactory;
    }

    /*
     * 代理创建SSLHttpRequest工厂
     *
     * @param
     * @return org.springframework.http.client.HttpComponentsClientHttpRequestFactory
     * @author yangk
     * @date 2021/11/22
     */
    public static HttpComponentsClientHttpRequestFactory requestSSLFactoryWithProxy()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {


        TrustStrategy trustStrategy = (certificate, type) -> true;

        SSLContext sslContext = org.apache.http.conn.ssl.SSLContexts.custom().
                useProtocol("TLSv1.2").loadTrustMaterial(null, trustStrategy).build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).setProxy(new HttpHost(PROXYHOST, PROXYPORT)).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());

        requestFactory.setHttpClient(httpClient);

        requestFactory.setConnectTimeout(TIMEOUT);

        return requestFactory;
    }
}
