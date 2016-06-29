package com.yimayhd.sellerAdmin.util;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class  HttpRequestUtil{

    //请求器是否已经初始化
    private boolean hasInit = false;

    //连接超时时间，默认10秒
    private int socketTimeout = 3000;

    //传输超时时间，默认30秒
    private int connectTimeout = 2000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    public HttpRequestUtil() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        init();
    }

    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        httpClient = HttpClients.createDefault();
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        hasInit = true;
    }
    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认3秒
     */
    public void setSocketTimeout(int socketTimeout) {
        socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认2秒
     */
    public void setConnectTimeout(int connectTimeout) {
        connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 个性化配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        requestConfig = requestConfig;
    }

    public String sendGet(String url) throws Exception {
        String result;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }
        finally {
            httpGet.abort();
        }

        return result;
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param postData 数据
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */

    public String sendPost(String url, String postData) throws Exception {

        if (!hasInit) {
            init();
        }
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postData, "UTF-8");
        httpPost.addHeader("Content-Type", "text/html");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);
        //Util.log("executing request" + httpPost.getRequestLine());
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }



    /*public String sendPostData(String url ,Object xmlObj) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(10000).build();
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml",xmlObj.getClass());
        String postData = xStreamForRequestPostData.toXML(xmlObj);
        System.out.println("my request");
        System.out.println(postData);
        StringEntity postDataEntity = new StringEntity(postData,"utf-8");
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpPost.setHeader("Content-Type", "text/xml");
        httpPost.setEntity(postDataEntity);
        httpPost.setConfig(requestConfig);
        String result;
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");
            System.out.println("my response");
            System.out.println(result);
        }finally {
            httpPost.abort();
        }

        return result;
    }*/
}
