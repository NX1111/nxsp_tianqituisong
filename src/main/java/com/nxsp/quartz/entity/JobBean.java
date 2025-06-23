package com.nxsp.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobBean {
    private String jobName;
    private String cronExpression;
    private String jobClass;
}
