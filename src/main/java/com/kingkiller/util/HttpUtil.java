package com.kingkiller.util;

import com.alibaba.fastjson.JSONObject;
import com.kingkiller.config.MyX509TrustManager;
import com.kingkiller.enums.CustomerConstant;
import com.kingkiller.exception.CustomerErrorCode;
import com.kingkiller.exception.CustomerServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;


import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Http公共类
 * @author kingkiller
 */
@Slf4j
public class HttpUtil {

    /**
     * 信任任何网站初始化
     */
    static {
        trustEveryone();
    }


    /**
     * 发送请求 POST
     *
     * @param url   请求地址
     * @param data  请求数据  json格式
     * @return  接口返回结果  JSON对象
     * @throws Exception
     */
    public static JSONObject postJSONData(String url, String data) throws Exception {
        HttpClient httpclient = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.setRequestEntity(new StringRequestEntity(data, "application/json", "UTF-8"));
        httpclient.executeMethod(method);
        if(method.getStatusCode() != CustomerConstant.SUCCESS_CODE) {
            method.releaseConnection();
            throw new IllegalArgumentException(method.getStatusCode() + ":"+ method.getStatusText());
        }
        data = method.getResponseBodyAsString();
        method.releaseConnection();
        log.info(":{}",data);
        return JSONObject.parseObject(data);
    }

    /**
     * 信任任何网站
     */
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送请求 GET
     * @param url  请求url
     * @return
     * @throws Exception
     */
    public static JSONObject getData(String url) throws Exception {

        HttpClient httpclient = new HttpClient();
        GetMethod method = new GetMethod(url);
        httpclient.executeMethod(method);
        if(method.getStatusCode() != CustomerConstant.SUCCESS_CODE ) {
            method.releaseConnection();
            throw new IllegalArgumentException(method.getStatusCode() + ":"+ method.getStatusText());
        }
        String data = method.getResponseBodyAsString();

        method.releaseConnection();
        log.info("data：{}",data);
        return JSONObject.parseObject(data);

    }


    /**
     * 发送https请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }

    /**
     * post请求（处理响应中文乱码版）
     *
     * @param url 请求地址
     * @param data 请求参数
     * @return JSONObject JSON对象
     * @throws Exception 异常
     */
    public static JSONObject post(String url, String data) throws Exception {
        HttpClient httpclient = new HttpClient();
        log.info("url:{}", url);
        PostMethod method = new PostMethod(url);
        method.setRequestEntity(new StringRequestEntity(data, "application/json", "UTF-8"));
        httpclient.executeMethod(method);
        if(method.getStatusCode() != CustomerConstant.SUCCESS_CODE) {
            method.releaseConnection();
            throw new IllegalArgumentException(method.getStatusCode() + ":"+ method.getStatusText());
        }
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); //这里设置具体编码，视具体接口而定
        data = method.getResponseBodyAsString();
        log.info("data:{}", data);
        method.releaseConnection();
        return JSONObject.parseObject(data);
    }


    /**
     * 发送请求 POST
     *
     * @param url   请求地址
     * @param data  请求数据  json格式
     * @return  接口返回结果  流
     * @throws Exception
     */
    public static InputStream postData(String url, String data) throws Exception {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity entity = new StringEntity(data);
        entity.setContentType("image/png");
        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);
        return response.getEntity().getContent();
    }

    /**
     * 下载文件
     *
     * @param urlPath 从url上下载文件
     * @param outPath 输出文件
     */
    public static void downFile(String urlPath, String outPath) {

        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 获取输入流
            try (InputStream in = conn.getInputStream();
                 BufferedInputStream bis = new BufferedInputStream(in);
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outPath))) {
                byte[] readSize = new byte[1024];
                int size;
                while ((size = bis.read(readSize)) != -1) {
                    bos.write(readSize, 0, size);
                }
            }
        } catch (IOException e) {
            log.info("下载文件异常，", e);
            throw new CustomerServiceException(CustomerErrorCode.DOWNLOAD_FILE_ERROR);
        }
    }

}
