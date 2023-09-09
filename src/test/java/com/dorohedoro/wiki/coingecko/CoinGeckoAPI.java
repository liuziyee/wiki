package com.dorohedoro.wiki.coingecko;

import com.dtflys.forest.annotation.HTTPProxy;
import com.dtflys.forest.annotation.Request;
import com.dtflys.forest.backend.httpclient.HttpClient;

import java.util.List;

public interface CoinGeckoAPI {

    @HttpClient
    @Request(
            url = "https://api.coingecko.com/api/v3/coins/list?include_platform=true",
            type = "GET",
            dataType = "json"
    )
    @HTTPProxy(host = "59.60.210.105", port = "8089")
    List<Coin> getCoins();
}
