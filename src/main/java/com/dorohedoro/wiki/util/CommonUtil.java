package com.dorohedoro.wiki.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/12 14:46
 */
public class CommonUtil {
    public static String[] split(String str, String... separators) {
        StringBuilder sb = new StringBuilder("[");
        for (String separator : separators) {
            sb.append(separator);
        }
        sb.append("]");
        return str.split(sb.toString());
    }
}
