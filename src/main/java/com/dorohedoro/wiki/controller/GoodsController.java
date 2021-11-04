package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.bean.ResponseBean;
import com.dorohedoro.wiki.service.GoodsService;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseBean getGoodsList(@RequestBody Goods goods) {
        List<GoodsVO> goodsList = goodsService.getGoodsList(goods);

        ResponseBean<List<GoodsVO>> res = new ResponseBean<>();
        res.setCode(AppEnum.ResultCode.normal.v());
        res.setData(goodsList);
        return res;
    }
}
