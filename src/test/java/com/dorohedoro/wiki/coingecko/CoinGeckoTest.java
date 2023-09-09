package com.dorohedoro.wiki.coingecko;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CoinGeckoTest {

    @Autowired
    private CoinGeckoAPI coinGeckoAPI;
    
    @Test
    public void getCoins() {
        List<Coin> coins = coinGeckoAPI.getCoins();
        coins.forEach(o -> log.info(o.toString()));
    }
}
