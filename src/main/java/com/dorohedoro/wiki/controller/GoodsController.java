package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.domain.Goods;
import com.dorohedoro.wiki.bean.vo.GoodsVO;
import com.dorohedoro.wiki.bean.vo.PageBean;
import com.dorohedoro.wiki.bean.vo.ResponseBean;
import com.dorohedoro.wiki.service.GoodsService;
import com.dorohedoro.wiki.util.ResCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    
    @Autowired
    private GoodsService goodsService;
    

    @GetMapping("/all")
    public ResponseBean getGoodsList(@Valid Goods goodsBO) {
        PageBean<GoodsVO> pageBean = goodsService.getGoodsList(goodsBO);

        ResponseBean<PageBean> res = new ResponseBean<>();
        res.setCode(ResCode.success.getCode());
        res.setData(pageBean);
        return res;
    }

    @PostMapping("/addOrUpd")
    public ResponseBean addOrUpdGoods(@RequestBody Goods goodsBO) {
        ResponseBean res = new ResponseBean();
        goodsService.addOrUpdGoods(goodsBO);
        res.setCode(ResCode.success.getCode());
        return res;
    }

    @GetMapping("/del/{id}")
    public ResponseBean del(@PathVariable Long id) {
        goodsService.del(id);
        ResponseBean res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        return res;
    }
}
