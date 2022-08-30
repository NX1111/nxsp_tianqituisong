package com.nxsp.nxsp_tianqituisong.utils;

import com.nxsp.nxsp_tianqituisong.DateUtils.Lunar;
import com.nxsp.nxsp_tianqituisong.DateUtils.LunarSolarConverter;
import com.nxsp.nxsp_tianqituisong.DateUtils.Solar;
import com.nxsp.nxsp_tianqituisong.configure.PushConfigureProperties;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JiNianRiUtils {

    //计算恋爱天数，恋爱周年
    public static Map<String, Integer> getLianAi(){
        return calculationLianAi(PushConfigureProperties.getLoveDate());
    }
    //计算阳历生日
    public static Map<String, Integer> getBirthdayBySolar(){
        return calculationBirthdayBySolar(PushConfigureProperties.getBirthday());
    }

    //计算阴历生日
    public static Map<String, Integer> getBirthdayByLunar(){
        return calculationBirthdayByLunar(PushConfigureProperties.getBirthday());
    }



    // 计算阳历生日倒计时天数，几岁生日
    public static Map<String, Integer> calculationBirthdayBySolar(String clidate)  {
        Map<String, Integer> map = new HashMap<>();
        int oldYears = 0;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cToday = Calendar.getInstance();
        Calendar cBirth = Calendar.getInstance();
        try {
            cBirth.setTime(myFormatter.parse(clidate));
            oldYears = cToday.get(Calendar.YEAR) - cBirth.get(Calendar.YEAR);
            map.put("oldYears",oldYears);
        } catch (ParseException e) {
            System.out.println("生日时间或者格式错误："+e.getMessage());
            e.printStackTrace();
        }
        cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR));
        int days;
        if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {

            days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            days += cBirth.get(Calendar.DAY_OF_YEAR);
        } else {

            days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
        }

        map.put("days",days);

        return map;
    }



    //计算阴历生日倒计时
    public static Map<String, Integer> calculationBirthdayByLunar(String clidate)  {

        Solar solar = new Solar();
        solar.parseDate(clidate);
        // 转农历
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
       // System.out.println("出生那年的阴历日期:" + lunar);
        // 获取今年生日阳历日期
        lunar.setLunarYear(Calendar.getInstance().getWeekYear());
        solar = LunarSolarConverter.LunarToSolar(lunar);
       // System.out.println("今年生日的阳历日期:" + solar);
        return calculationBirthdayBySolar(solar.getStringDate());

    }





    // 计算恋爱天数，周年，周年倒计时
    public static Map<String,Integer> calculationLianAi(String date)   {
        HashMap<String,Integer> map = new HashMap<>();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lianaiToday = Calendar.getInstance();
        Calendar lianaiBirth = Calendar.getInstance();
        try {
            lianaiBirth.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            System.out.println("恋爱纪念日时间或者格式错误："+e.getMessage());
            e.printStackTrace();
        }
        int numberJiNian=0;
        numberJiNian = lianaiToday.get(Calendar.YEAR) - lianaiBirth.get(Calendar.YEAR) ;
        //今年是恋爱几周年
        map.put("lianAiYears",numberJiNian);
        //距离今年的恋爱纪念日还有多少天
        map.put("lianAiYearsLeft",calculationBirthdayBySolar(date).get("days"));

        int day = 0;
        try {
            long time = System.currentTimeMillis() - simpleDateFormat.parse(date).getTime();
            day = (int) (time / 86400000L);
            map.put("lianAiDays",day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return map;
    }

}
