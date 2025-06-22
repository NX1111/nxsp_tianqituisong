package com.nxsp.tianqituisong.controller;

import com.nxsp.tianqituisong.service.impl.TimedPushservice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/wechat")
public class WechatPushController {

    @Resource
    private TimedPushservice timedPushservice;

    /**
     * 手动触发推送接口
     * @return 推送结果信息
     */
    @GetMapping("/test")
    public String manualPush() {
        return timedPushservice.morningPush();
    }
}