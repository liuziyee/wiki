package com.dorohedoro.wiki.bean.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/8 9:47
 */
@Data
public class PageBean<T> {
    @NotNull
    private Integer page;
    @NotNull
    @Max(value = 1000, message = "size can't be greater than 1000")
    private Integer size;
    private List<T> list;
    private Long total;
}
