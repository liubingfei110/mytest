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
import java.util.HashMap;
import java.util.Map;

public class HttpTest2 {
    public static void main(String[] args) {
        String url = "https://restapi.amap.com/v3/geocode/regeo";
        String key = "d8a2c503477a6f595b02c6652d77520a";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("output", "JSON");
        paramMap.put("location", "120.864608,32.016212");
        paramMap.put("key", key);
        JSONObject positionObj = getAddressInfo(url, paramMap);
        System.out.println(positionObj.get("country"));
        System.out.println(positionObj.get("province"));
        System.out.println(positionObj.get("city"));
        System.out.println(positionObj.get("district"));
        System.out.println(positionObj.get("township"));
        System.out.println(positionObj.get("cityCode"));
        System.out.println(positionObj.get("adcode"));
        System.out.println(positionObj.get("buildingName"));
        System.out.println(positionObj.get("address"));
    }

    /**
     * 根据经纬度查询地址
     *
     * @param url      接口地址
     * @param paramMap 参数map
     * @return
     */
    public static JSONObject getAddressInfo(String url, Map<String, String> paramMap) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!paramMap.isEmpty()) {
            stringBuilder.append("?");
            paramMap.forEach((paramName, paramValue) -> stringBuilder.append(paramName + "=" + paramValue + "&"));
        }
        String stringBuilderStr = stringBuilder.toString();
        String lastStr = stringBuilderStr.substring(stringBuilderStr.length() - 1);
        if ("&".equals(lastStr)) {
            stringBuilderStr = stringBuilderStr.substring(0, stringBuilderStr.length() - 1);
        }
        url = url + stringBuilderStr;
        JSONObject positionObj = new JSONObject();

        try {
            // 请求高德接口
            String result = sendHttpGet(url);
            JSONObject resultJOSN = JSONObject.parseObject(result);
            System.out.println("高德接口返回原始数据：");
            System.out.println(resultJOSN);
            JSONObject jsonObject = resultJOSN.getJSONObject("regeocode");
            String address = jsonObject.getString("formatted_address");
            JSONObject addressJsonObject = jsonObject.getJSONObject("addressComponent");
            String country = addressJsonObject.getString("country");
            String province = addressJsonObject.getString("country");
            String cityCode = addressJsonObject.getString("cityCode");
            String city = addressJsonObject.getString("city");
            String adcode = addressJsonObject.getString("adcode");
            //JSONObject StringNumberJsonObject = addressJsonObject.getJSONObject("streetNumber");
            String district = addressJsonObject.getString("district");
            String township = addressJsonObject.getString("township");
            String buildingName = addressJsonObject.getJSONObject("building").getString("name");
            positionObj.put("country", country);
            positionObj.put("province", province);
            positionObj.put("city", city);
            positionObj.put("district", district);
            positionObj.put("township", township);
            positionObj.put("cityCode", cityCode);
            positionObj.put("adcode", adcode);
            positionObj.put("buildingName", buildingName);
            positionObj.put("address", address);
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
