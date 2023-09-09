package com.dorohedoro.wiki.ok;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OKTxFillResponse {

    public Integer code;
    public String msg;
    public List<Tx> data;

    @Data
    public static class Tx {
        String chainFullName;
        String chainShortName;
        String txid;
        String height;
        String transactionTime;
        String transactionType;
        BigDecimal amount;
        String transactionSymbol;
        String methodId;
        String errorLog;
        String inputData;
        BigDecimal txfee;
        String index;
        String confirm;
        String state;
        String gasLimit;
        String gasUsed;
        String gasPrice;
        String totalTransactionSize;
        String virtualSize;
        String weight;
        String nonce;
        List<Token20Tx> tokenTransferDetails;
        List<InternalTx> contractDetails;
    }
    
    @Data
    public static class Token20Tx {
        String txid;
        String index;
        String token;
        String tokenContractAddress;
        String symbol;
        String from;
        String to;
        boolean isFromContract;
        boolean isToContract;
        String tokenId;
        BigDecimal amount;
    }

    @Data
    public static class InternalTx {
        String index;
        String from;
        String to;
        boolean isFromContract;
        boolean isToContract;
        BigDecimal amount;
        String gasLimit;
    }
}
