package com.dorohedoro.wiki.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 17:55
 */
public class TimeUtil {
    private static String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static String getYMDHMS(Long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat(YMDHMS);
        return formatter.format(date);
    }
    
    public static HashMap<String, Long> getToday() {
        LocalDateTime ts = LocalDateTime.of(LocalDate.now(), LocalTime.MIN); //00:00:00
        LocalDateTime te = LocalDateTime.of(LocalDate.now(), LocalTime.MAX); //23:59:59

        Long tst = ts.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long tet = te.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        HashMap<String, Long> map = new HashMap<>();
        map.put("ts", tst);
        map.put("te", tet);
        return map;
    }
    
}
