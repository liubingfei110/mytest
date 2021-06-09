package com.liubingfei.mytest.test;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpTest {
    public static void main(String[] args) {
        String address = "上海交通大学徐汇校区";
        String key = "d8a2c503477a6f595b02c6652d77520a";
        JSONObject positionObj = getLngAndLat(address, key);
        String longitude = positionObj.getString("longitude");
        String latitude = positionObj.getString("latitude");
        System.out.println("经度：" + longitude);
        System.out.println("纬度：" + latitude);

    }

    /**
     * 根据地址查询经纬度
     *
     * @param address
     * @param key
     * @return
     */
    public static JSONObject getLngAndLat(String address, String key) {
        JSONObject positionObj = new JSONObject();

        try {
            // 拼接请求高德的url
            String url = "http://restapi.amap.com/v3/geocode/geo?address=" + address + "&output=JSON&key=" + key;
            // 请求高德接口
            String result = sendHttpGet(url);
            JSONObject resultJOSN = JSONObject.parseObject(result);
            System.out.println("高德接口返回原始数据：");
            System.out.println(resultJOSN);
            JSONArray geocodesArray = resultJOSN.getJSONArray("geocodes");
            if (geocodesArray.size() > 0) {
                String position = geocodesArray.getJSONObject(0).getString("location");
                String[] lngAndLat = position.split(",");
                String longitude = lngAndLat[0];
                String latitude = lngAndLat[1];
                positionObj.put("longitude", longitude);
                positionObj.put("latitude", latitude);
            }
            geocodesArray.getJSONObject(0).getString("location");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return positionObj;
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @return
     */
    public static String sendHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setSocketTimeout(10000)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        String result = "";
        try {
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
            HttpEntity entity = closeableHttpResponse.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
