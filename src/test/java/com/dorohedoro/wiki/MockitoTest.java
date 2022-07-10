package com.dorohedoro.wiki;

import com.dorohedoro.wiki.bean.domain.CategoryExample;
import com.dorohedoro.wiki.bean.domain.Goods;
import com.dorohedoro.wiki.bean.domain.GoodsExample;
import com.dorohedoro.wiki.bean.vo.PageBean;
import com.dorohedoro.wiki.mapper.CategoryMapper;
import com.dorohedoro.wiki.mapper.GoodsMapper;
import com.dorohedoro.wiki.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MockitoTest {

    @Mock
    private Random random;

    @Spy
    @InjectMocks
    private GoodsService goodsService;

    @Mock
    private GoodsMapper goodsMapper;

    @Mock
    private CategoryMapper categoryMapper;
    
    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void stub() {
        when(random.nextInt()).thenReturn(3);
        log.info("{}", random.nextInt());
    }

    @Test
    public void mockService() {
        Goods goods = new Goods();
        goods.setPage(1);
        goods.setSize(100);
        goods.setCategoryId(102L);

        when(categoryMapper.selectByExample(any(CategoryExample.class))).thenReturn(Collections.emptyList());
        when(goodsMapper.selectByExample(any(GoodsExample.class))).thenReturn(Collections.emptyList());

        PageBean res = new PageBean();
        res.setTotal(0L);
        res.setList(Collections.emptyList());

        assertEquals(res, goodsService.getGoodsList(goods));
    }
}
