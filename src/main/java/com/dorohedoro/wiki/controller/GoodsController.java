package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 9:45
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    
    @GetMapping("/list")
    public List<Goods> getGoodsList() {
        return goodsService.getGoodsList();
    }
}
