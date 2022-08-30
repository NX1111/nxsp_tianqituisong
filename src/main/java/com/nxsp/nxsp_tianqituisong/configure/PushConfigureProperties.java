package com.nxsp.nxsp_tianqituisong.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ConfigurationProperties("wechat")
public class PushConfigureProperties {
    /**
     * 微信公众平台的appID
     */
    private static String appId;
    /**
     * 微信公众平台的appSecret
     */
    private static String secret;
    /**
     * 天气查询的城市ID
     */
    private static String district_id;
    /**
     * 应用AK
     */
    private static String ak;
    /**
     * 纪念日
     */
    private static String loveDate;
    /**
     * 生日
     */
    private static String birthday;
    /**
     * 关注公众号的用户ID
     */
    private static List<String> userId;
    /**
     * 模板ID
     */
    private static String templateId;

    /**
     * 天行数据apiKey
     */
    private static String rainbowKey;



    public static String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        PushConfigureProperties.appId = appId;
    }

    public static String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        PushConfigureProperties.secret = secret;
    }

    public static String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        PushConfigureProperties.district_id = district_id;
    }

    public static String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        PushConfigureProperties.ak = ak;
    }

    public static String getLoveDate() {
        return loveDate;
    }

    public void setLoveDate(String loveDate) {
        PushConfigureProperties.loveDate = loveDate;
    }

    public static String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        PushConfigureProperties.birthday = birthday;
    }

    public static List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        PushConfigureProperties.userId = userId;
    }

    public static String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        PushConfigureProperties.templateId = templateId;
    }

    public static String getRainbowKey() {
        return rainbowKey;
    }

    public void setRainbowKey(String rainbowKey) {
        PushConfigureProperties.rainbowKey = rainbowKey;
    }


}
