package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.domain.AccessToken;
import com.dorohedoro.wiki.service.GithubApiFeignService;
import com.dorohedoro.wiki.service.GithubFeignService;
import com.dorohedoro.wiki.websocket.WSServer;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final GithubFeignService githubFeignService;
    private final GithubApiFeignService githubApiFeignService;
    private final WSServer wsServer;

    // 重定向地址
    @GetMapping("/authorization_code/github")
    public void authorizationCodeByGithub(@RequestParam String code, @RequestParam String state) {
        log.info("授权码: {}, websocket标识码: {}", code, state);
        HashMap<String, Object> map = new HashMap<>();
        map.put("client_id", "e45ae824184497e69d10");
        map.put("client_secret", "6e75e0358141a2cbfb9bf9d3b63ae3a9b936681d");
        map.put("code", code);
        
        AccessToken accessToken = githubFeignService.getAccessToken(map);
        log.info("github访问令牌: {}", accessToken);

        Map<String, Object> userDetails = githubApiFeignService.getUserDetails("Bearer " + accessToken.getAccess_token());
        log.info("用户信息: {}", userDetails);

        wsServer.unicastMsg(new Gson().toJson(userDetails), state);
    }
}
