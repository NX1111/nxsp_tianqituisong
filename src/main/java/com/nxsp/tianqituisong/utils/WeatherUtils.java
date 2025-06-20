package com.nxsp.tianqituisong.utils;

import com.nxsp.tianqituisong.pojo.Weather;
import org.springframework.stereotype.Component;
import com.nxsp.tianqituisong.configure.PushConfigureProperties;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Component
public class WeatherUtils {

    @Resource
    private PushConfigureProperties pushConfig;

    @Resource
    private RestTemplate restTemplate;

    public Weather getWeather() {
        Map<String, String> map = new HashMap<>();
        // 使用注入的配置对象获取配置值
        map.put("district_id", pushConfig.getDistrictId());
        map.put("data_type", "all");
        map.put("ak", pushConfig.getAk());

        String res = restTemplate.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class,
                map);

        JSONObject json = JSONObject.parseObject(res);
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");
        Weather weather = weathers.get(0);

        JSONObject location = json.getJSONObject("result").getJSONObject("location");
        weather.setTemp(now.getString("temp"));
        weather.setCity(location.getString("city"));
        weather.setText_now(now.getString("text"));

        return weather;
    }
}