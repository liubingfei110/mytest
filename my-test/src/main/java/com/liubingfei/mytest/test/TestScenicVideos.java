package com.liubingfei.mytest.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuBingFei
 * @desc
 * @date 2024/8/6 15:06
 * @return
 */
@Slf4j
public class TestScenicVideos {

    public String getVideos(String ip ,String port, String appKey, String secret, String methodStr, JSONObject jsonBody){
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = ip + ":" + port; // 平台的ip端口
        ArtemisConfig.appKey = appKey;  // 密钥appkey
        ArtemisConfig.appSecret = secret;// 密钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + methodStr;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };

        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */
        String body = jsonBody.toJSONString();

        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
        return result;

    }

    /**
     * 查询视频列表
     */
    @Test
    public void callAndGetVideoList(){
        String ip = "61.243.10.142";
        String port = "442";
        String appKey = "20893172";
        String secret = "G77MGfQ9peH6HtiyLV4P";
        String methodStr = "/api/resource/v1/cameras";
        JSONObject params = new JSONObject();
        params.put("pageNo", 1);
        params.put("pageSize", 10);
        String result = getVideos(ip, port, appKey, secret, methodStr, params);
        // 使用 fastjson 解析 JSON 字符串为 Map
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        List<Map<String, Object>> videoList = JSON.parseObject(dataObject.getJSONArray("list").toJSONString(), new TypeReference<List<Map<String, Object>>>() {});
        log.info("返回结果：" + videoList);
    }

    /**
     * 查询视频详情
     */
    @Test
    public void callAndGetVideoDetail(){
        String ip = "61.243.10.142";
        String port = "442";
        String appKey = "20893172";
        String secret = "G77MGfQ9peH6HtiyLV4P";
        String methodStr = "/api/video/v2/cameras/previewURLs";
        JSONObject params = new JSONObject();
        params.put("cameraIndexCode", "52020201191310000007");
        params.put("protocol", "hls");
        String result = getVideos(ip, port, appKey, secret, methodStr, params);
        try{
        // 将 JSON 字符串解析为 JSONObject
        JSONObject jsonObject = JSON.parseObject(result);

        // 获取 code 值
        String code = jsonObject.getString("code");

        // 判断 code 是否等于 "0"
            if ("0".equals(code)) {
                // 获取 data 对象
                JSONObject dataObject = jsonObject.getJSONObject("data");

                // 获取 url 值
                String url = dataObject.getString("url");
                log.info("URL: " + url);
            } else {
                // 获取错误信息
                String msg = jsonObject.getString("msg");
                throw new Exception("Error occurred: " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
