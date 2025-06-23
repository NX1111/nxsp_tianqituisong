package com.nxsp.quartz.controller;

import com.nxsp.quartz.entity.JobBean;
import com.nxsp.quartz.utils.JobUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartz")
public class QuartzController {
    public static final Logger logger = LoggerFactory.getLogger(QuartzController.class);

    @Autowired(required = false) // 设置为可选注入
    private Scheduler scheduler;

    public static String JOB_NAME = "myjob_1";


    @GetMapping("/create")
    public String createJob(){
        JobBean jobBean = new JobBean();
        jobBean.setJobName(JOB_NAME);
        jobBean.setCronExpression("0/2 * * * * ?");
        jobBean.setJobClass("com.nxsp.quartz.job.MyJob");
        JobUtils.createJob(scheduler,jobBean);
        return "success";
    }

    @GetMapping("/resume")
    public String resumeJob(){
        JobUtils.resumeJob(scheduler,JOB_NAME);
        return "success";
    }

    @GetMapping("/pause")
    public String pauseJob(){
        JobUtils.pauseJob(scheduler,JOB_NAME);
        return "success";
    }

    @GetMapping("/delete")
    public String deleteJob(){
        JobUtils.deleteJob(scheduler,JOB_NAME);
        return "success";
    }




}
