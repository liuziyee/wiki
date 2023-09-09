package com.dorohedoro.wiki.alchemy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AlchemyTest {

    @Autowired
    private AlchemyAPI alchemyAPI;
    
    @Test
    public void getAddressAllTxs() {
        String address = "0x3DdfA8eC3052539b6C9549F12cEA2C295cfF5296";
        List<TX> fromTxs = getAddressTxs(address, true, false);
        List<TX> toTxs = getAddressTxs(address, false, false);
        List<TX> bothTxs = getAddressTxs(address, false, true);
        log.info("from txs: {}, to txs: {}, both txs: {}", fromTxs.size(), toTxs.size(), bothTxs.size());
    }

    public List<TX> getAddressTxs(String address, boolean isFrom, boolean isBoth) {
        String toBlock = "latest";
        Map<String, Object> params = new HashMap<>();
        List<TX> txs = new ArrayList<>();
        params.put("fromBlock", "0x0");
        params.put("toBlock", toBlock);
        params.put(isFrom ? "fromAddress" : "toAddress", address);
        if (isBoth) {
            params.put("fromAddress", address);
            params.put("toAddress", address);
        }
        params.put("category", Arrays.asList("external", "internal", "erc20"));
        params.put("excludeZeroValue", false);
        params.put("maxCount", "0x3e8");

        APIRequest apiRequest = new APIRequest();
        apiRequest.setMethod("alchemy_getAssetTransfers");
        apiRequest.setParams(Collections.singletonList(params));

        APIResponse apiResponse = alchemyAPI.getAddressTxs(apiRequest);

        APIResponse.Data data = apiResponse.getResult();
        txs.addAll(data.getTransfers());
        String pageKey = data.getPageKey();
        if (StringUtils.hasText(pageKey)) {
            // 还有下一页
            params.put("pageKey", pageKey);
            apiRequest.setParams(Collections.singletonList(params));
            getAddressTxsPage(apiRequest, txs);
        }
        return txs;
    }

    public void getAddressTxsPage(APIRequest apiRequest, List<TX> txs) {
        if (apiRequest == null) {
            return;
        }

        APIResponse apiResponse = alchemyAPI.getAddressTxs(apiRequest);
        Map<String, Object> params = apiRequest.getParams().get(0);
        APIResponse.Data data = apiResponse.getResult();
        txs.addAll(data.getTransfers());
        String pageKey = data.getPageKey();
        if (StringUtils.hasText(pageKey)) {
            // 还有下一页
            params.put("pageKey", pageKey);
            apiRequest.setParams(Collections.singletonList(params));
            getAddressTxsPage(apiRequest, txs);
        }
    }
}
