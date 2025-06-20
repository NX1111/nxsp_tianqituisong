package com.nxsp.tianqituisong.utils;

import com.alibaba.fastjson.JSON;
import com.nxsp.tianqituisong.configure.PushConfigureProperties;
import com.nxsp.tianqituisong.pojo.Weather;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PushUtils {
    @Resource
    private PushConfigureProperties pushConfig;
    @Resource
    private WeatherUtils weatherUtils;
    @Resource
    private CaiHongPiUtils caiHongPiUtils;
    @Resource
    private JiNianRiUtils jiNianRiUtils;

    public String push() {
        // 配置微信服务
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
        wxStorage.setAppId(pushConfig.getAppId()); // 使用注入的配置
        wxStorage.setSecret(pushConfig.getSecret()); // 使用注入的配置

        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();

        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId(pushConfig.getTemplateId()) // 使用注入的配置
                .build();

        // 获取天气信息
        Weather weather = weatherUtils.getWeather();

        // 配置模板消息数据
        templateMessage.addData(new WxMpTemplateData("love", "❤", "#FF0000"));
        templateMessage.addData(new WxMpTemplateData("riqi", weather.getDate() + "  " + weather.getWeek(), "#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("tianqi", weather.getText_now(), "#0000FF"));
        templateMessage.addData(new WxMpTemplateData("city", weather.getCity(), "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("low", weather.getLow() + "", "#7CFC00"));
        templateMessage.addData(new WxMpTemplateData("temp", weather.getTemp() + "", "#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high", weather.getHigh() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("wc_day", weather.getWc_day() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("wd_day", weather.getWd_day() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("caihongpi", caiHongPiUtils.getCaiHongPi(), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai", jiNianRiUtils.getLianAi().get("lianAiDays") + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("solarBirth", jiNianRiUtils.getBirthdayBySolar().get("days") + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("lunarBirth", jiNianRiUtils.getBirthdayByLunar().get("days") + "", "#FF1493"));

        String solarBirthLogo = " ", lianaiLogo = " ", lunarBirthLogo = " ";

        // 周年纪念日祝福语
        if (jiNianRiUtils.getLianAi().get("lianAiYearsLeft") == 0) {
            lianaiLogo = "❤" + " 今天是我们恋爱" + jiNianRiUtils.getLianAi().get("lianAiYears") + "周年纪念日！天天开心鸭！" + "❤";
        }

        // 阳历生日祝福语
        if (jiNianRiUtils.getBirthdayBySolar().get("days") == 0) {
            solarBirthLogo = "❤" + " 宝贝破壳" + jiNianRiUtils.getBirthdayBySolar().get("oldYears") + "年啦！祝生日快乐鸭！" + "❤";
        }

        // 农历生日祝福语
        if (jiNianRiUtils.getBirthdayByLunar().get("days") == 0) {
            lunarBirthLogo = "❤" + " 宝贝破壳" + jiNianRiUtils.getBirthdayBySolar().get("oldYears") + "年啦！祝生日快乐鸭！" + "❤";
        }

        templateMessage.addData(new WxMpTemplateData("solarBirthLogo", solarBirthLogo, "#FF0000"));
        templateMessage.addData(new WxMpTemplateData("lunarBirthLogo", lunarBirthLogo, "#FF0000"));
        templateMessage.addData(new WxMpTemplateData("lianaiLogo", lianaiLogo, "#FF0000"));

        StringBuilder result = new StringBuilder();
        int totalCount = pushConfig.getUserId().size(); // 使用注入的配置
        List<String> sucessId = new ArrayList<>();
        List<String> failId = new ArrayList<>();

        for (String id : pushConfig.getUserId()) { // 使用注入的配置
            templateMessage.setToUser(id);
            try {
                templateMsgService.sendTemplateMsg(templateMessage);
                System.out.println(JSON.toJSONString(templateMessage));
                result.append("给用户").append(id).append("的推送：成功<br/>");
                System.out.println("给用户" + id + "的推送：成功");
                sucessId.add(id);
            } catch (WxErrorException e) {
                result.append("给用户").append(id).append("的推送：失败<br/>");
                System.out.println("给用户" + id + "的推送：失败" + e.getMessage());
                failId.add(id);
                e.printStackTrace();
            }
        }

        if (totalCount == sucessId.size()) {
            result.append("全部推送成功：").append(totalCount);
        } else {
            result.append("推送成功：").append(sucessId.size()).append("/").append(totalCount);
        }

        return result.toString();
    }
}