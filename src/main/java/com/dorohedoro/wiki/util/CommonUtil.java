package com.dorohedoro.wiki.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/12 14:46
 */
public class CommonUtil {
    public static String[] split(String str, String... separators) {
        String regExp = Stream.of(separators).collect(Collectors.joining("", "[", "]"));
        return str.split(regExp);
    }
}
