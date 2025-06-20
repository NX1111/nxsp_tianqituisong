package com.nxsp.tianqituisong.utils;

import com.nxsp.tianqituisong.DateUtils.Lunar;
import com.nxsp.tianqituisong.DateUtils.LunarSolarConverter;
import com.nxsp.tianqituisong.DateUtils.Solar;
import com.nxsp.tianqituisong.configure.PushConfigureProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class JiNianRiUtils {

    @Resource
    private PushConfigureProperties pushConfig;

    @Autowired
    public JiNianRiUtils(PushConfigureProperties pushConfig) {
        this.pushConfig = pushConfig;
    }

    /**
     * 计算恋爱天数，恋爱周年
     */
    public Map<String, Integer> getLianAi() {
        return calculationLianAi(pushConfig.getLoveDate());
    }

    /**
     * 计算阳历生日
     */
    public Map<String, Integer> getBirthdayBySolar() {
        return calculationBirthdayBySolar(pushConfig.getBirthday());
    }

    /**
     * 计算阴历生日
     */
    public Map<String, Integer> getBirthdayByLunar() {
        return calculationBirthdayByLunar(pushConfig.getBirthday());
    }

    /**
     * 计算阳历生日倒计时天数和年龄
     */
    public Map<String, Integer> calculationBirthdayBySolar(String birthDate) {
        Map<String, Integer> map = new HashMap<>();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate today = LocalDate.now();
            LocalDate birthday = LocalDate.parse(birthDate, formatter);

            // 计算年龄
            int age = Period.between(birthday, today).getYears();
            map.put("oldYears", age);

            // 计算今年生日日期
            LocalDate nextBirthday = birthday.withYear(today.getYear());
            if (nextBirthday.isBefore(today)) {
                nextBirthday = nextBirthday.plusYears(1);
            }

            // 计算距离生日的天数
            long daysUntilBirthday = ChronoUnit.DAYS.between(today, nextBirthday);
            map.put("days", (int) daysUntilBirthday);

        } catch (Exception e) {
            // 在实际应用中应该记录日志
            map.put("oldYears", 0);
            map.put("days", 0);
        }

        return map;
    }

    /**
     * 计算阴历生日倒计时
     */
    public Map<String, Integer> calculationBirthdayByLunar(String birthDate) {
        try {
            Solar solar = new Solar();
            solar.parseDate(birthDate);

            // 转农历
            Lunar lunar = LunarSolarConverter.SolarToLunar(solar);

            // 获取今年生日阳历日期
            lunar.setLunarYear(Calendar.getInstance().get(Calendar.YEAR));
            Solar thisYearSolar = LunarSolarConverter.LunarToSolar(lunar);

            return calculationBirthdayBySolar(thisYearSolar.getStringDate());
        } catch (Exception e) {
            // 在实际应用中应该记录日志
            Map<String, Integer> map = new HashMap<>();
            map.put("oldYears", 0);
            map.put("days", 0);
            return map;
        }
    }

    /**
     * 计算恋爱天数，周年，周年倒计时
     */
    public Map<String, Integer> calculationLianAi(String startDate) {
        Map<String, Integer> map = new HashMap<>();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate today = LocalDate.now();
            LocalDate start = LocalDate.parse(startDate, formatter);

            // 计算恋爱周年数
            int anniversaryYears = Period.between(start, today).getYears();
            map.put("lianAiYears", anniversaryYears);

            // 计算今年纪念日日期
            LocalDate nextAnniversary = start.withYear(today.getYear());
            if (nextAnniversary.isBefore(today)) {
                nextAnniversary = nextAnniversary.plusYears(1);
            }

            // 计算距离纪念日的天数
            long daysUntilAnniversary = ChronoUnit.DAYS.between(today, nextAnniversary);
            map.put("lianAiYearsLeft", (int) daysUntilAnniversary);

            // 计算恋爱总天数
            long totalDays = ChronoUnit.DAYS.between(start, today);
            map.put("lianAiDays", (int) totalDays);

        } catch (Exception e) {
            // 在实际应用中应该记录日志
            map.put("lianAiYears", 0);
            map.put("lianAiYearsLeft", 0);
            map.put("lianAiDays", 0);
        }

        return map;
    }
}