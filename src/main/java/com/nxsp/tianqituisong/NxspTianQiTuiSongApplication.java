package com.nxsp.tianqituisong;

import com.nxsp.tianqituisong.configure.PushConfigureProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling // 开启定时任务
public class NxspTianQiTuiSongApplication {

    private static final Logger logger = LoggerFactory.getLogger(NxspTianQiTuiSongApplication.class);

    @Resource
    private PushConfigureProperties pushConfig;

    @PostConstruct
    public void checkConfig() {
        logger.info("===== 配置检查 =====");
        logger.info("AppId: {}", pushConfig.getAppId());
        logger.info("DistrictId: {}", pushConfig.getDistrictId());
        logger.info("UserId: {}", pushConfig.getUserId());
        logger.info("birthday: {}", pushConfig.getBirthday());


        if(pushConfig.getAppId() == null) {
            logger.error("配置加载失败！");
        } else {
            logger.info("配置加载成功！");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(NxspTianQiTuiSongApplication.class, args);
    }

    /**
     * 配置定时任务线程池
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("scheduled-task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }
}