package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.domain.Goods;
import com.dorohedoro.wiki.bean.VO.PageBean;
import com.dorohedoro.wiki.bean.VO.ResponseBean;
import com.dorohedoro.wiki.service.GoodsService;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.bean.VO.GoodsVO;
import com.dorohedoro.wiki.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

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
    public ResponseBean getGoodsList(@Valid Goods goodsBO) {
        PageBean<GoodsVO> pageBean = goodsService.getGoodsList(goodsBO);

        ResponseBean<PageBean> res = new ResponseBean<>();
        res.setCode(ResultCode.success.getCode());
        res.setData(pageBean);
        return res;
    }

    @PostMapping("/addOrUpd")
    public ResponseBean addOrUpdGoods(@RequestBody Goods goodsBO) {
        ResponseBean res = new ResponseBean();
        goodsService.addOrUpdGoods(goodsBO);
        res.setCode(ResultCode.success.getCode());
        return res;
    }

    @GetMapping("/del/{id}")
    public ResponseBean del(@PathVariable("id") Long id) {
        goodsService.del(id);
        ResponseBean res = new ResponseBean();
        res.setCode(ResultCode.success.getCode());
        return res;
    }
}
