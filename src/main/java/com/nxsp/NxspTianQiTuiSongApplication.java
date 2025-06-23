package com.nxsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling // 开启定时任务
public class NxspTianQiTuiSongApplication {
    public static void main(String[] args) {
        SpringApplication.run(NxspTianQiTuiSongApplication.class, args);
    }

}