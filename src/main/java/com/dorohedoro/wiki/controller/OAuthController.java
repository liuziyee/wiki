package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.domain.AccessToken;
import com.dorohedoro.wiki.service.GithubFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final GithubFeignService githubFeignService;

    // 回调地址
    @GetMapping("/authorization_code/github")
    public void authorizationCodeByGithub(@RequestParam String code, @RequestParam(required = false) String state) {
        log.info("授权码: {}", code);
        HashMap<String, Object> map = new HashMap<>();
        map.put("client_id", "e45ae824184497e69d10");
        map.put("client_secret", "6e75e0358141a2cbfb9bf9d3b63ae3a9b936681d");
        map.put("code", code);
        AccessToken accessToken = githubFeignService.getAccessToken("application/json", map);
        log.info("github访问令牌: {}", accessToken);
    }
}
