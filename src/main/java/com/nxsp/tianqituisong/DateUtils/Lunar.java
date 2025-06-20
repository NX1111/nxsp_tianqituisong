package com.nxsp.tianqituisong.DateUtils;

import lombok.Data;

/**
 * 阴历
 */
@Data
public class Lunar {
    public int lunarYear;
    public int lunarMonth;
    public int lunarDay;
    public boolean isLeap;
}