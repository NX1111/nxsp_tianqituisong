package com.nxsp.nxsp_tianqituisong;

import com.nxsp.nxsp_tianqituisong.utils.PushUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling // 开启定时任务
public class NxspTianQiTuiSongApplication {

    public static void main(String[] args) {
        SpringApplication.run(NxspTianQiTuiSongApplication.class, args);
    }

    // 定时 早7点推送  0秒 0分 7时
    @Scheduled(cron = "0 30 7 * * ?")
    public void goodMorning(){
        PushUtils.push();
    }
}
