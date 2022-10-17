package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.domain.AccessToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "github", url = "https://github.com/login/oauth")
public interface GithubFeignService {

    // 获取访问令牌
    @PostMapping(value = "/access_token", headers = "accept=application/json")
    AccessToken getAccessToken(@RequestBody Map<String, Object> map);
}
