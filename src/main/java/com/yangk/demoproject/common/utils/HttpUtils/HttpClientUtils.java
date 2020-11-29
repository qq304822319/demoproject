package com.yangk.demoproject.common.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author yangk
 * @date 2020/11/22
 */
@Slf4j
public class HttpClientUtils {

    private static PoolingHttpClientConnectionManager connectionManager;

    private static String PROXYHOST;

    private static int PROXYPORT;
    private static int MAX_TOTAL_CONNECTIONS;
    private static int MAX_ROUTE_CONNECTIONS;


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

        TrustStrategy acceptingTrusStrategy = (certificate, type) -> {
            return true;
        };

        SSLContext sslContext = org.apache.http.conn.ssl.SSLContexts.custom().
                useProtocol("TLSv1.2").loadTrustMaterial(null, acceptingTrusStrategy).build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());

        requestFactory.setHttpClient(httpClient);

        return requestFactory;
    }
}
