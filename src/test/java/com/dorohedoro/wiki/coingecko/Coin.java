package com.dorohedoro.wiki.coingecko;

import lombok.Data;

import java.util.Map;

@Data
public class Coin {

    private String id;

    private String symbol;

    private String name;

    private Map<String, String> platforms;
}
