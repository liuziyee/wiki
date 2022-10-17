package com.dorohedoro.wiki.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{token}")
@Slf4j
public class WSServer {

    private String token = "";

    private static Map<String, Session> map = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        map.put(token, session);
        this.token = token;
        log.info("got a new websocket connection: [token:{}, session id:{}, current connections:{}]", token, session.getId(), map.size());
    }

    @OnClose
    public void onClose(Session session) {
        map.remove(this.token);
        log.info("close a websocket connection: [token:{}, session id:{}, current connections:{}]", this.token, session.getId(), map.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("got a message: [token:{}, msg:{}]", token, message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("there is a problem", error);
    }

    // 广播消息
    public void broadcastMsg(String message, String uniToken) {
        for (String token : map.keySet()) {
            if (token.equals(uniToken)) {
                continue;
            }
            Session session = map.get(token);
            boolean isSucceed = false;
            try {
                session.getBasicRemote().sendText(message);
                isSucceed = true;
            } catch (IOException e) {
                log.error("failed to push a message: [token:{}, msg:{}]", token, message);
            } finally {
                if (isSucceed) {
                    log.info("push a message: [token:{}, msg:{}]", token, message);
                }
            }
        }
    }

    // 单播消息
    public void unicastMsg(String message, String uniToken) {
        Session session = map.get(uniToken);
        try {
            session.getBasicRemote().sendText(message);
            log.info("push a message: [token:{}, msg:{}]", token, message);
        } catch (IOException e) {
            log.error("failed to push a message: [token:{}, msg:{}]", token, message);
        }
    }
}

