package com.nxsp.tianqituisong.configure;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "wechat")
@RefreshScope
@Data
public class PushConfigureProperties {
    /**
     * 微信公众平台的appID
     */
    private String appId;

    /**
     * 微信公众平台的appSecret
     */
    private String secret;

    /**
     * 天气查询的城市ID - 使用驼峰命名
     */
    private String districtId; // 修改为驼峰命名

    /**
     * 应用AK
     */
    private String ak;

    /**
     * 纪念日
     */
    private String loveDate;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 关注公众号的用户ID
     */
    private List<String> userId;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 天行数据apiKey
     */
    private String rainbowKey;
}