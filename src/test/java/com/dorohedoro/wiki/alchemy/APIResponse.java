package com.dorohedoro.wiki.alchemy;

import lombok.Data;

import java.util.List;

@Data
public class APIResponse {

    private String jsonrpc;

    private int id;

    private Data result;

    @lombok.Data
    class Data {
        List<TX> transfers;

        String pageKey;
    }
}
