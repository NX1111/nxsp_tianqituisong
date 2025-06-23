package com.nxsp.quartz.job;

import org.quartz.*;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        System.out.println("执行定时任务名字：" + jobDetail.getKey().getName());
        System.out.println("执行定时任务组：" + jobDetail.getKey().getGroup());
        System.out.println("执行任务类：" + jobDetail.getJobClass().getName());
        System.out.println("本次执行事件：" + context.getFireTime());
        System.out.println("下次执行事件：" + context.getNextFireTime());
    }
}
