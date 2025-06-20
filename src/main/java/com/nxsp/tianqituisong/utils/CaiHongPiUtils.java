package com.nxsp.tianqituisong.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nxsp.tianqituisong.configure.PushConfigureProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class CaiHongPiUtils {

    @Resource
    private PushConfigureProperties pushConfig;

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    public CaiHongPiUtils(PushConfigureProperties pushConfig, RestTemplate restTemplate) {
        this.pushConfig = pushConfig;
        this.restTemplate = restTemplate;
    }

    /**
     * 获取彩虹屁内容
     */
    public String getCaiHongPi() {
        String rainbowKey = pushConfig.getRainbowKey();
        if (rainbowKey == null || rainbowKey.isEmpty()) {
            return "未配置彩虹屁API密钥";
        }

        String apiUrl = "http://api.tianapi.com/caihongpi/index?key=" + rainbowKey;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JSONObject jsonObject = JSONObject.parseObject(response.getBody());
                JSONArray newslist = jsonObject.getJSONArray("newslist");
                if (newslist != null && !newslist.isEmpty()) {
                    return newslist.getJSONObject(0).getString("content");
                }
            }
            return "未获取到彩虹屁内容";
        } catch (Exception e) {
            // 在实际应用中应该记录日志
            return "彩虹屁API调用失败: " + e.getMessage();
        }
    }
}