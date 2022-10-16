package com.dorohedoro.wiki.bean.domain;

import lombok.Data;

@Data
public class AccessToken {

    private String access_token;

    private String scope;

    private String token_type;
}
