package com.dorohedoro.wiki.alchemy;

import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Request;
import com.dtflys.forest.annotation.Retry;
import com.dtflys.forest.backend.httpclient.HttpClient;

public interface AlchemyAPI {

    @HttpClient
    @Request(
            url = "https://eth-mainnet.g.alchemy.com/v2/Cm2fjqYNd2x2IB-6-MGugOBhn85y2v36",
            type = "POST"
    )
    @Retry(maxRetryCount = "3", maxRetryInterval = "10")
    APIResponse getAddressTxs(@JSONBody APIRequest apiRequest);
}
