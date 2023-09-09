package com.dorohedoro.wiki.alchemy;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TX {

    private String blockNum;

    private String uniqueId;

    private String hash;

    private String from;

    private String to;

    private BigDecimal value;
    
    // TODO erc721TokenId erc1155Metadata tokenId

    private String asset; // 代币简称

    private String category; // 交易类型

    private RawContract rawContract;
    
    @Data
    class RawContract {
        private String value;

        private String address; // 代币合约地址

        private String decimal;
    }
}
