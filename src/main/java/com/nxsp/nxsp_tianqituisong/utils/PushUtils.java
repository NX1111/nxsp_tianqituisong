package com.nxsp.nxsp_tianqituisong.utils;

import com.nxsp.nxsp_tianqituisong.configure.PushConfigureProperties;
import com.nxsp.nxsp_tianqituisong.pojo.Weather;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.ArrayList;
import java.util.List;


public class PushUtils {


    public static String push(){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(PushConfigureProperties.getAppId());
        wxStorage.setSecret(PushConfigureProperties.getSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
         WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId(PushConfigureProperties.getTemplateId())
                .build();
        // 配置你的信息
        Weather weather = WeatherUtils.getWeather();
        templateMessage.addData(new WxMpTemplateData("love","❤","#FF0000"));
        //当前日期
        templateMessage.addData(new WxMpTemplateData("riqi",weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));
        //今天天气
        templateMessage.addData(new WxMpTemplateData("tianqi",weather.getText_now(),"#0000FF"));
        //区域编码对应的城市名
        templateMessage.addData(new WxMpTemplateData("city",weather.getCity(),"#FF6347"));
        //最低温度
        templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "","#7CFC00"));
        //
        templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D"));
        //最高温度
        templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" ));
        //白天风力
        templateMessage.addData(new WxMpTemplateData("wc_day",weather.getWc_day()+ "","#FF6347" ));
        //白天风向
        templateMessage.addData(new WxMpTemplateData("wd_day",weather.getWd_day()+ "","#FF6347" ));
        //彩虹屁的句子
        templateMessage.addData(new WxMpTemplateData("caihongpi", CaiHongPiUtils.getCaiHongPi(),"#FF69B4"));
        //恋爱纪念日倒计时多少天
        templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi().get("lianAiDays")+"","#FF1493"));
        //阳历生日倒计时多少天
        templateMessage.addData(new WxMpTemplateData("solarBirth",JiNianRiUtils.getBirthdayBySolar().get("days")+"","#FF1493"));
        //阴历生日倒计时多少天
        templateMessage.addData(new WxMpTemplateData("lunarBirth",JiNianRiUtils.getBirthdayByLunar().get("days")+"","#FF1493"));



        String solarBirthLogo = " ",lianaiLogo = " ", lunarBirthLogo =" ";

        //周年纪念日祝福语
        if(JiNianRiUtils.getLianAi().get("lianAiYearsLeft") == 0){
            lianaiLogo = "❤"+" 今天是我们恋爱" + JiNianRiUtils.getLianAi().get("lianAiYears") + "周年纪念日！天天开心鸭！" + "❤";
        }

        //阳历生日祝福语
        if(JiNianRiUtils.getBirthdayBySolar().get("days")  == 0  ){
            solarBirthLogo = "❤"+ " 宝贝破壳" + JiNianRiUtils.getBirthdayBySolar().get("oldYears") +  "年啦！祝生日快乐鸭！"+"❤";
        }
        //农历生日祝福语
        if( JiNianRiUtils.getBirthdayByLunar().get("days") ==0 ){
            lunarBirthLogo = "❤"+ " 宝贝破壳" + JiNianRiUtils.getBirthdayBySolar().get("oldYears") +  "年啦！祝生日快乐鸭！"+"❤";
        }
        templateMessage.addData(new WxMpTemplateData("solarBirthLogo",solarBirthLogo,"#FF0000"));
        templateMessage.addData(new WxMpTemplateData("lunarBirthLogo",lunarBirthLogo,"#FF0000"));
        templateMessage.addData(new WxMpTemplateData("lianaiLogo",lianaiLogo,"#FF0000"));


        StringBuilder result = new StringBuilder();
        int totalCount = 0;
        List<String> userId = PushConfigureProperties.getUserId();
        totalCount = userId.size();
        List<String> sucessId = new ArrayList<>();
        List<String> failId = new ArrayList<>();

        for(String id:userId){
            templateMessage.setToUser(id);
            try {
                templateMsgService.sendTemplateMsg(templateMessage);
                result.append("给用户"+ id + "的推送：成功" + "<br/>");
                System.out.println("给用户"+ id + "的推送：成功");
                sucessId.add(id);
            } catch (WxErrorException e) {
                result.append("给用户"+ id + "的推送：失败" + "<br/>");
                System.out.println("给用户"+ id + "的推送：失败" + e.getMessage());
                failId.add(id);
                e.printStackTrace();
            }
        }

        if(totalCount == sucessId.size()){
            result.append("全部推送成功：" + totalCount );
        }else{
            result.append("推送成功：" + sucessId.size() + "/" + totalCount);
        }



        return result.toString();





    }
}
