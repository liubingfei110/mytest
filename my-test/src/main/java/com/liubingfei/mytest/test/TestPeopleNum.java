package com.liubingfei.mytest.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.liubingfei.mytest.test.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestPeopleNum {

    /**
     * 查询景区实时列表
     */
    @Test
    public void callScenicList() {
        try {
            // 创建JSON对象并设置请求参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "荔波小七孔景区");
            jsonObject.put("pageNo", 1);
            jsonObject.put("pageSize", 10);
            String jsonString = jsonObject.toString();

            // 获取当前时间戳
            long timestamp = System.currentTimeMillis();
            System.out.println("timestamp: " + timestamp);

            // 生成签名
            String prepareSignString = jsonString + timestamp;
            String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALLNwR/JCizCeH638+t3Zu7Ve8NyjzT//9Mc8GiA4Hh0fJTEwfCddVTeBtxe65N6KbW/N5u8YeI+xo4C9nucdfTEGyQNkXJHPrG+a6O5EFqzkBxvUWjwxWxvzCEgsAkOVasGa9y7B4iuHx1qUNtqfE4itJDwg79lpQlM3FUQL2F3AgMBAAECgYATyRjZv5FUqqBSxWBEag4F17G+Sur13m4cmlMv/TLZoWo3vB4J1iKppXf04rwGsbnz1pj8rrfWJUQrasCDpSiYIdCaDsquVcjWTq81Bj+RyP281t5K51ldwe5xj1Jwq9bhLDa+0eDdBqtQ0DZgv4XgXzUN9/lGETUqjNEXG6TM4QJBANjV/YRuOReZY45FuJVRhto8w2U1x7a+E+cbcogDdKgoKWEAFLmHCAF9EnyEk33/fiOePKzvpvsdGVnSnDPEsU0CQQDTGT223HDx1ApJXrEx/GP/L0bNkA8qWH+H1fo381KEsX62gmhjyj+82/Q2Q2wBJtLsZe0COoBA50HrAIvuxrvTAkEAtuE0or779AVwzAvNtFJAdEwPWItVV7FUw/SIUmLnvvOwgpk44YkbnXXJY6/FXd1gm9XXwtuVb/GI8hMJnzfjeQJAA+kNyZtfxDsHTcChS6m/OHTpvNkUTtaA1kVSc0LgnnCjRkI4KNEQSTFR3sd08riDY7mnoEjxI0KlZ/MEpJGt3QJBAMejTl5aq9adzqwjdWQ7EtUbWdy/tJGKX7X80Y/UaWhko0gj9Nf+BNF3DOGkX1mUSmx+DZIXgCWF7+Pn4+3ivl4=";
            String sign = RSAUtil.sign(prepareSignString.getBytes(), RSAUtil.decodeBase64(privateKey));
            System.out.println("sign: " + sign);

            // 设置URL和连接属性
            String url = "https://103.3.152.193:31599/leader-cockpit-external-api/openService/queryScenicRealTimeReception";

            // 创建忽略SSL验证的HttpClient
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial((chain, authType) -> true)
                    .build();

            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(30 * 1000) // 连接超时：30秒
                    .setSocketTimeout(30 * 1000) // 读取超时：30秒
                    .build();
            CloseableHttpClient client = HttpClientBuilder.create()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setDefaultRequestConfig(requestConfig)
                    .build();

            HttpPost post = new HttpPost(url);

            // 设置请求头部信息
            post.setHeader("Content-Type", "application/json");
            post.setHeader("timestamp", String.valueOf(timestamp));
            post.setHeader("sign", sign);

            // 设置请求体
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            post.setEntity(entity);

            // 发送请求
            HttpResponse response = client.execute(post);
            int responseCode = response.getStatusLine().getStatusCode();
            System.out.println("POST Response Code :: " + responseCode);

            // 处理响应
            if (responseCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 使用 fastjson 解析 JSON 字符串为 Map
                JSONObject responseObject = JSON.parseObject(responseBody);
                JSONObject dataObject = responseObject.getJSONObject("data");
                JSONArray records = dataObject.getJSONArray("records");
                List<Map<String, Object>> scenicList = JSON.parseObject(records.toJSONString(), List.class);
                log.info("返回结果：" + scenicList);
            } else {
                System.out.println("POST request not worked");
            }

            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}