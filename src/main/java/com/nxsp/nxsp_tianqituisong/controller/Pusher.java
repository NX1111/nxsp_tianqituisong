package com.nxsp.nxsp_tianqituisong.controller;

import com.nxsp.nxsp_tianqituisong.pojo.Weather;
import com.nxsp.nxsp_tianqituisong.utils.CaiHongPiUtils;
import com.nxsp.nxsp_tianqituisong.utils.JiNianRiUtils;
import com.nxsp.nxsp_tianqituisong.utils.WeatherUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

public class Pusher {

    public static void main(String[] args) {
        push();
    }
    private static String appId = "wx0266eb953c41931a";
    private static String secret = "ef2720d5e0dd1d01dcefe4a8daa72ab8";



    public static void push(){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("oeTfm6B4a5hxOHbmo_xkdmrMJSTg")
                .templateId("nh7RzTAvwvwaEHJfPZMsFOGAWhAoQ1wCN-rCGnaeID4")
                .build();
        // 配置你的信息
        Weather weather = WeatherUtils.getWeather();
        templateMessage.addData(new WxMpTemplateData("riqi",weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("tianqi",weather.getText_now(),"#0000FF"));
        templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "","#7CFC00"));
        templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" ));
        templateMessage.addData(new WxMpTemplateData("caihongpi", CaiHongPiUtils.getCaiHongPi(),"#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi()+"","#FF1493"));
        templateMessage.addData(new WxMpTemplateData("shengri",JiNianRiUtils.getBirthday_Jo()+"","#FFA500"));

        String beizhu = "❤";
        if(JiNianRiUtils.getLianAi() % 365 == 0){
            beizhu = "今天是恋爱" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！";
        }
        if(JiNianRiUtils.getBirthday_Jo()  == 0){
            beizhu = "今天是生日，生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
