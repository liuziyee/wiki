package com.dorohedoro.wiki.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(value = "github-api", url = "https://api.github.com")
public interface GithubApiFeignService {

    @GetMapping("/user")
    Map<String, Object> getUserDetails(@RequestHeader("Authorization") String accessToken);
}
