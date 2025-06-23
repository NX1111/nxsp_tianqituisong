package com.nxsp.quartz.job;

import com.nxsp.tianqituisong.utils.PushUtils;
import org.quartz.*;

import javax.annotation.Resource;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJob implements Job {
    @Resource
    private PushUtils pushUtils;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        pushUtils.push();
        System.out.println("执行定时任务名字：" + jobDetail.getKey().getName());
        System.out.println("执行任务类：" + jobDetail.getJobClass().getName());
        System.out.println("本次执行事件：" + context.getFireTime());
        System.out.println("下次执行事件：" + context.getNextFireTime());
    }
}
