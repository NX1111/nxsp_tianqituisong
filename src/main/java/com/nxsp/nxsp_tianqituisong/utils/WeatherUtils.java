package com.nxsp.nxsp_tianqituisong.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nxsp.nxsp_tianqituisong.configure.PushConfigureProperties;
import com.nxsp.nxsp_tianqituisong.pojo.Weather;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherUtils {

    public static Weather getWeather(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("district_id", PushConfigureProperties.getDistrict_id());
        map.put("data_type","all");
        map.put("ak",PushConfigureProperties.getAk());
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
