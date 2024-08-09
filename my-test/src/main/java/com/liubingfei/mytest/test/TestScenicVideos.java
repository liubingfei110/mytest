package com.liubingfei.mytest.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
     * 1. 分页查询区域列表，获取parentIndexCode
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
     * 2. 查询区域信息：parentIndexCode：e63f5260-24c9-4cdb-824d-d2a3f590e432， regionName：樟江景区，获取indexCode=b075a535-7a00-41fe-a5e5-b70aa60952e8
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

    /**
     * 3. 查询监控点列表，传参：监控点编号：regionIndexCodes=b075a535-7a00-41fe-a5e5-b70aa60952e8，获取：监控点唯一标识：indexCode：b99814f234854a84b28ef24f4ede794e
     * 一共查询到16个监控视频
     */
    @Test
    public void callAndGetVideoList(){
        String ip = "61.243.10.142";
        String port = "442";
        String appKey = "20893172";
        String secret = "G77MGfQ9peH6HtiyLV4P";
        String methodStr = "/api/resource/v2/camera/search";
        JSONObject params = new JSONObject();
        params.put("pageNo", 1);
        params.put("pageSize", 1000);
        params.put("regionIndexCodes", new ArrayList<>(Arrays.asList("b075a535-7a00-41fe-a5e5-b70aa60952e8")));
        String result = getVideos(ip, port, appKey, secret, methodStr, params);
        // 使用 fastjson 解析 JSON 字符串为 Map
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        List<Map<String, Object>> videoList = JSON.parseObject(dataObject.getJSONArray("list").toJSONString(), new TypeReference<List<Map<String, Object>>>() {});
        log.info("分页查询返回结果：" + videoList);

    }

    /**
     * 4. 查询视频流地址 传参：监控点唯一标识：indexCode=b99814f234854a84b28ef24f4ede794e，获取url
     * 参数：
     * 1：b99814f234854a84b28ef24f4ede794e    大七孔左栈道悬崖侧枪机
     * 2：46444053c14d4673a42a0d7db1b0d9c3    小七孔东门入口_客流统计
     * 3：5654941ce1024030ad4e6ff7c54e6e1e    小七孔东门出口_客流统计
     * 4：4e56190738314ea99d3e53cde1231d31    小七孔桥球机右
     * 5：ee998fa2eaeb4b29b2870704fc57481e    新西门全景外
     * 6：38a5b3bbfb464fd5a9f3fe5c16c1434a    新西门全景外球机（重点）
     * 7：cba2d05b463045818a3a39d81f2e7de8    西门咨询台
     * 8：e5702161cc364fbb9346256953599f13    西门入口长廊枪机
     * 9：c220d6c8fc1e4d38a4d90d454bb36b6d    大七孔LED屏前
     * 10：ff0c4d61a7ce43cea98cecb9d8dd9bbf   大七孔候车亭全景
     * 11：496c1abc742a4522b51edbe596f07cbf   新西门出口闸机人脸1
     * 12：60197f0afe774a0499bdc95f05876586   拉关湖左栈道球机
     * 13：e22018a00975401b9836757c744c23b5   大七孔新桥头球机
     * 14：35538ed829df464284d50c0427ae52d6   大七孔候车亭全景球机
     * 15: 92f0f70f0bef471180f3dba2748c8d29   努内吉海左栈道枪机
     * 16: f171e56490964ce3a3877e16c13c9849   上己定湖VIP休息室枪机2
     */
    @Test
    public void callAndGetVideoDetail(){
        String ip = "61.243.10.142";
        String port = "442";
        String appKey = "20893172";
        String secret = "G77MGfQ9peH6HtiyLV4P";
        String methodStr = "/api/video/v2/cameras/previewURLs";
        JSONObject params = new JSONObject();
        params.put("cameraIndexCode", "f171e56490964ce3a3877e16c13c9849");
        params.put("streamform", "ps");
        params.put("streamType", "0");
        params.put("transmode", "1");
        params.put("expand", "transcode=0");
        params.put("protocol", "hls");
        log.info("入参：" +params.toJSONString());
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
