package com.dorohedoro.wiki.bean.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 18:31
 */
@Data
public class GoodsVO {
    //Long转为String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    
    private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    private Long parentCid;

    private String description;

    private String cover;

    private Long viewCount;

    private Long followCount;

    private Long commentCount;

    private Long createTime;

    private Long updateTime;

}
