package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.bean.PageBean;
import com.dorohedoro.wiki.bean.ResponseBean;
import com.dorohedoro.wiki.service.GoodsService;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.bean.GoodsVO;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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
    public ResponseBean getGoodsList(Goods req) {
        PageBean<GoodsVO> pageBean = goodsService.getGoodsList(req);

        ResponseBean<PageBean> res = new ResponseBean<>();
        res.setCode(AppEnum.ResultCode.normal.v());
        res.setData(pageBean);
        res.setServerTime(Instant.now().toEpochMilli());
        return res;
    }
}
