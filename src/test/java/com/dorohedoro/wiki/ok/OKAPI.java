package com.dorohedoro.wiki.ok;

import com.dtflys.forest.annotation.*;

public interface OKAPI {

    @Request(
            url = "https://www.oklink.com/api/v5/explorer/transaction/transaction-fills",
            type = "GET",
            dataType = "json",
            headers = {
                    "Ok-Access-Key: ${apikey}"
            }
    )
    @Retry(maxRetryCount = "3", maxRetryInterval = "10")
    OKTxFillResponse transactionDetailList(@Var("apikey") String apikey, @Query("chainShortName") String chainShortName, @Query("txid") String txid);
}
