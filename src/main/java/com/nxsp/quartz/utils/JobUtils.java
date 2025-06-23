package com.nxsp.quartz.utils;

import com.nxsp.quartz.entity.JobBean;
import org.quartz.*;

public class JobUtils {

    public static void createJob(Scheduler scheduler, JobBean jobBean) {
        Class<? extends Job> jobClass = null;
        JobDetail jobDetail = null;
        Trigger cronTrigger = null;
        try {
            jobClass = (Class<? extends Job>) Class.forName(jobBean.getJobClass());
            jobDetail = JobBuilder.newJob(jobClass)
                    .storeDurably()
                    .usingJobData("count", "1")
                    .withIdentity(jobBean.getJobName())
                    .build();

            cronTrigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobBean.getJobName() + "cronTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobBean.getCronExpression()))
                    .build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void pauseJob(Scheduler scheduler, String jobName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }



    public static void resumeJob(Scheduler scheduler, String jobName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


    public static void deleteJob(Scheduler scheduler, String jobName) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobName));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
