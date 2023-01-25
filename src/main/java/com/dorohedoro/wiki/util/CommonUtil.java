package com.dorohedoro.wiki.util;

import com.dorohedoro.wiki.bean.vo.ResponseBean;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonUtil {
    
    private static Gson gson = new Gson();
    
    public static String[] split(String str, String... separators) {
        String regExp = Stream.of(separators).collect(Collectors.joining("", "[", "]"));
        return str.split(regExp);
    }

    public static void sayError(HttpServletResponse response, ResCode code) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ResponseBean resBean = new ResponseBean();
        resBean.setCode(code.getCode());
        resBean.setMsg(code.getDesc());
        response.getWriter().write(gson.toJson(resBean));
    }
}
