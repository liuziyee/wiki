package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 9:45
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    
    public List<Goods> getGoodsList() {
        return goodsMapper.selectByExample(null);
    }
    
}
