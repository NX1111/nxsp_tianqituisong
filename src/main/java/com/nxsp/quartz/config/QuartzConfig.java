package com.nxsp.quartz.config;

import com.nxsp.quartz.job.MyJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    // 定义JobDetail
    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(MyJob.class)
                .withIdentity("sampleJob")
                .usingJobData("param", "任务参数示例")
                .storeDurably() // 持久化存储
                .build();
    }

    // 定义Cron Trigger（可选）
    @Bean
    public Trigger cronJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(sampleJobDetail())
                .withIdentity("cronTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ? *")) //
                .build();
    }
}