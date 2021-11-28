package com.dorohedoro.wiki.bean.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/23 17:20
 */
@Data
public class UserVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String loginName;

    private String name;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
