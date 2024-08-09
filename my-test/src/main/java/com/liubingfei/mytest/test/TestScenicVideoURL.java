package com.liubingfei.mytest.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TestScenicVideoURL {

    private String ip = "61.243.10.142";
    private String port = "442";
    private String appKey = "20893172";
    private String secret = "G77MGfQ9peH6HtiyLV4P";

    public String getVideos(String methodStr, JSONObject jsonBody) {
        ArtemisConfig.host = ip + ":" + port;
        ArtemisConfig.appKey = appKey;
        ArtemisConfig.appSecret = secret;

        final String ARTEMIS_PATH = "/artemis";
        final String apiUrl = ARTEMIS_PATH + methodStr;
        Map<String, String> path = new HashMap<String, String>(2) {{
            put("https://", apiUrl);
        }};

        String contentType = "application/json";
        String body = jsonBody.toJSONString();

        return ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);
    }

    public String getVideoStreamUrl(String cameraIndexCode, String protocol) {
        String methodStr = "/api/video/v2/cameras/previewURLs";
        JSONObject params = new JSONObject();
        params.put("cameraIndexCode", cameraIndexCode);
        params.put("protocol", protocol);

        String result = getVideos(methodStr, params);
        JSONObject jsonObject = JSON.parseObject(result);

        if ("0".equals(jsonObject.getString("code"))) {
            return jsonObject.getJSONObject("data").getString("url");
        } else {
            log.error("Error getting stream URL for camera {}: {}", cameraIndexCode, jsonObject.getString("msg"));
            return null;
        }
    }

    @Test
    public void getVideoListWithStreamUrls() {
        String methodStr = "/api/resource/v1/cameras";
        JSONObject params = new JSONObject();
        params.put("pageNo", 1);
        params.put("pageSize", 10);

        String result = getVideos(methodStr, params);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray listArray = dataObject.getJSONArray("list");

        List<JSONObject> videoList = listArray.stream().map(item -> {
            JSONObject video = (JSONObject) item;
            String cameraIndexCode = video.getString("cameraIndexCode");
            String streamUrl = getVideoStreamUrl(cameraIndexCode, "hls");
            video.put("streamUrl", streamUrl);
            return video;
        }).collect(Collectors.toList());

        log.info("Video list with stream URLs: " + JSON.toJSONString(videoList));
    }
}