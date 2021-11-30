package com.dorohedoro.wiki.util;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/26 8:51
 */
public enum ResCode {
    success(0L, "成功"),
    valid(10L, "参数校验失败"),
    db(20L, "数据库异常"),
    user_exists(30L, "用户已存在"),
    login(40L, "用户名或密码错误"),
    unauthorized(50L, "无访问权限");

    private Long code;
    private String desc;

    ResCode(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Long getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
