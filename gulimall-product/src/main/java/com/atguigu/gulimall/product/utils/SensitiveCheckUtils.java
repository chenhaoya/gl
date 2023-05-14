package com.atguigu.gulimall.product.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author ChenHao
 * @date 2023/5/11 23:47
 */
public class SensitiveCheckUtils {
    public static String checkWord(String text) {
        String str = null;
        try {
            //创建httpClient实例
            CloseableHttpClient client = HttpClients.createDefault();
            //创建一个uri对象
            URIBuilder uriBuilder = new URIBuilder("http://127.0.0.1:10009/aaa");
            //塞入form参数
            uriBuilder.addParameter("text", text);
            //创建httpGet远程连接实例,这里传入目标的网络地址
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 设置请求头信息，鉴权(没有可忽略)
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数(没有可忽略)
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            //执行请求
            CloseableHttpResponse response = client.execute(httpGet);
            //获取Response状态码
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            //获取响应实体, 响应内容
            HttpEntity entity = response.getEntity();
            //通过EntityUtils中的toString方法将结果转换为字符串
            str = EntityUtils.toString(entity);
            response.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }
        return str;
    }
}