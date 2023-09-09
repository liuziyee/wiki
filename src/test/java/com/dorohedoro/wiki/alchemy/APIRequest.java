package com.dorohedoro.wiki.alchemy;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class APIRequest {
    
    private int id = 1;
    
    private String jsonrpc = "2.0";
    
    private String method;

    private List<Map> params;
    
    //private List<Param> params;
    //
    //class Param {
    //    private String fromBlock;
    //
    //    private String toBlock;
    //
    //    private String fromAddress;
    //
    //    private String toAddress;
    //
    //    private List<String> category;
    //
    //    private boolean withMetadata;
    //
    //    private boolean excludeZeroValue;
    //
    //    private String maxCount;
    //
    //    private String pageKey;
    //}
}
