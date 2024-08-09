package com.liubingfei.mytest.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * @author LiuBingFei
 * @desc
 * @date 2024/8/6 15:06
 * @return
 */
@Slf4j
public class TestScenicRegion {

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
     * 分页查询区域列表
     */
    @Test
    public void callAndGetRegionList(){
        String ip = "61.243.10.142";
        String port = "442";
        String appKey = "20893172";
        String secret = "G77MGfQ9peH6HtiyLV4P";
        String methodStr = "/api/resource/v1/regions";
        JSONObject params = new JSONObject();
        params.put("pageNo", 1);
        params.put("pageSize", 1000);
        String result = getVideos(ip, port, appKey, secret, methodStr, params);
        // 使用 fastjson 解析 JSON 字符串为 Map
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        List<Map<String, Object>> videoList = JSON.parseObject(dataObject.getJSONArray("list").toJSONString(), new TypeReference<List<Map<String, Object>>>() {});
        log.info("分页查询返回结果：" + videoList);

    }

    /**
     * 查询区域信息：樟江景区
     */
    @Test
    public void callAndGetRegionDetail(){
        String ip = "61.243.10.142";
        String port = "442";
        String appKey = "20893172";
        String secret = "G77MGfQ9peH6HtiyLV4P";
        String methodStr = "/api/irds/v2/region/nodesByParams";
        JSONObject params = new JSONObject();
        params.put("pageNo", 1);
        params.put("pageSize", 1000);
        params.put("resourceType", "camera");
        params.put("parentIndexCodes", new ArrayList<>(Arrays.asList("e63f5260-24c9-4cdb-824d-d2a3f590e432")));
        params.put("regionName", "樟江景区");
        String result = getVideos(ip, port, appKey, secret, methodStr, params);
        // 使用 fastjson 解析 JSON 字符串为 Map
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        List<Map<String, Object>> videoList = JSON.parseObject(dataObject.getJSONArray("list").toJSONString(), new TypeReference<List<Map<String, Object>>>() {});
        log.info("分页查询返回结果：" + videoList);

    }


}
