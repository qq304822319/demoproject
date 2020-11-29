package com.yangk.demoproject.common.utils.HttpUtils;

import com.sun.org.apache.bcel.internal.generic.IFNE;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.Request.Builder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp3工具类
 *
 * @author yangk
 * @date 2020/11/22
 */
@Slf4j
public class OkhttpUtils {

    private final static String SUCCESS_CODE = "200";
    public final static int READ_TIMEOUT = 100;
    public final static int CONNECT_TIMEOUT = 80;
    public final static int WRITE_TIMEOUT = 60;

    private final static MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private final static byte[] LOCKER = new byte[0];
    private static OkhttpUtils instance;
    private OkHttpClient okHttpClient;

    private OkhttpUtils() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        client.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        client.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        client.sslSocketFactory(createSSLSocketFactory(), new OkhttpUtils.TrustAllCerts());
        client.hostnameVerifier((s, sslSession) -> {
            return true;
        });
        okHttpClient = client.build();
    }

    public static OkhttpUtils getInstance() {
        if (instance == null) {
            synchronized (LOCKER) {
                instance = new OkhttpUtils();
            }
        }

        return instance;
    }

    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new OkhttpUtils.TrustAllCerts()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;

    }

    class TrustAllCerts implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static String filterSpecialChar(String str) {
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("'", "&apos;");

        return str;
    }

    /**************************请求方法***************************/

    /*
     * OkHttp get请求
     *
     * @param url
     * @param haedMap
     * @return okhttp3.Response
     * @author yangk
     * @date 2020/11/22
     */
    public Response get(String url, Map<String, String> haedMap) {

        url = filterSpecialChar(url);
        URL url1;
        Request.Builder builder = new Request.Builder().url(url);

        if (haedMap != null) {
            for (String headKey : haedMap.keySet()) {
                builder = builder.addHeader(headKey, haedMap.get(headKey));
            }
        }

        Request request = builder.build();

        Call call = okHttpClient.newCall(request);

        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            log.error("okHttp error");
            throw new RuntimeException("okHttp error");
        }
    }

    public Response get(String url) {
        return get(url, null);
    }

    /*
     * OkHttp post请求
     *
     * @param url
     * @param requestBodyJson
     * @param haedMap
     * @return okhttp3.Response
     * @author yangk
     * @date 2020/11/22
     */
    public Response post(String url, String requestBodyJson, Map<String, String> haedMap) {
        Request.Builder builder = new Request.Builder();
        if (haedMap != null) {
            for (String headKey : haedMap.keySet()) {
                builder = builder.addHeader(headKey, haedMap.get(headKey));
            }
        }
        RequestBody requestBody = RequestBody.create(JSON, requestBodyJson);
        Request request = builder.post(requestBody).url(url).build();
        Call call = okHttpClient.newCall(request);

        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            log.error("okHttp error");
            throw new RuntimeException("okHttp error");
        }
    }

    public Response post(String url, String requestBodyJson) {
        return post(url, requestBodyJson, null);
    }

    /*
     * OkHttp put请求
     *
     * @param url
     * @param requestBodyJson
     * @param haedMap
     * @return okhttp3.Response
     * @author yangk
     * @date 2020/11/22
     */
    public Response put(String url, String requestBodyJson, Map<String, String> haedMap) {
        Request.Builder builder = new Request.Builder();
        if (haedMap != null) {
            for (String headKey : haedMap.keySet()) {
                builder = builder.addHeader(headKey, haedMap.get(headKey));
            }
        }
        RequestBody requestBody = RequestBody.create(JSON, requestBodyJson);
        Request request = builder.put(requestBody).url(url).build();
        Call call = okHttpClient.newCall(request);

        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            log.error("okHttp error");
            throw new RuntimeException("okHttp error");
        }
    }

    public Response put(String url, String requestBodyJson) {
        return put(url, requestBodyJson, null);
    }

    /*
     * OkHttp patch请求
     *
     * @param url
     * @param requestBodyJson
     * @param haedMap
     * @return okhttp3.Response
     * @author yangk
     * @date 2020/11/22
     */
    public Response patch(String url, String requestBodyJson, Map<String, String> haedMap) {
        Request.Builder builder = new Request.Builder();
        if (haedMap != null) {
            for (String headKey : haedMap.keySet()) {
                builder = builder.addHeader(headKey, haedMap.get(headKey));
            }
        }
        RequestBody requestBody = RequestBody.create(JSON, requestBodyJson);
        Request request = builder.patch(requestBody).url(url).build();
        Call call = okHttpClient.newCall(request);

        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            log.error("okHttp error");
            throw new RuntimeException("okHttp error");
        }
    }

    public Response patch(String url, String requestBodyJson) {
        return patch(url, requestBodyJson, null);
    }


}
