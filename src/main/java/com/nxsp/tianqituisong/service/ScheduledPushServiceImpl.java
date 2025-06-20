package com.nxsp.tianqituisong.service;

import com.nxsp.tianqituisong.service.impl.TimedPushservice;
import com.nxsp.tianqituisong.utils.PushUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ScheduledPushServiceImpl implements TimedPushservice {

    @Resource
    private PushUtils pushUtils;


    /**
     * 定时推送任务 - 每天早上7:30执行
     */
    @Scheduled(cron = "0 30 7 * * ?")
    @Override
    public String morningPush() {
        String result;
        try {
            result = pushUtils.push();
            // 可以添加日志记录或结果处理
        } catch (Exception e) {
            // 异常处理
            log.error("推送微信公众号消息异常", e);
            result = "推送微信公众号消息异常";
        }
        return result;
    }

}