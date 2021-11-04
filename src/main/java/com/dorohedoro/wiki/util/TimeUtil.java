package com.dorohedoro.wiki.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 17:55
 */
public class TimeUtil {
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static String getYMDHMS() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YMDHMS);
        String str = formatter.format(LocalDateTime.now());
        return str;
    }
}
