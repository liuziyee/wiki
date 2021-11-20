package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.bean.PageBean;
import com.dorohedoro.wiki.bean.ResponseBean;
import com.dorohedoro.wiki.service.CategoryService;
import com.dorohedoro.wiki.service.GoodsService;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.bean.GoodsVO;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public ResponseBean getGoodsList(@Valid Goods reqBean) {
        PageBean<GoodsVO> pageBean = goodsService.getGoodsList(reqBean);

        ResponseBean<PageBean> res = new ResponseBean<>();
        res.setCode(AppEnum.ResultCode.success.v());
        res.setData(pageBean);
        res.setServerTime(Instant.now().toEpochMilli());
        return res;
    }

    @PostMapping("/addOrUpd")
    public ResponseBean addOrUpdGoods(@RequestBody Goods reqBean) {
        ResponseBean<Long> res = new ResponseBean();
        Long code = goodsService.addOrUpdGoods(reqBean);
        if (code.equals(AppEnum.ResultCode.db.v())) {
            res.setCode(code);
        } else {
            res.setCode(AppEnum.ResultCode.success.v());
            res.setData(code);
        }
        return res;
    }

    @GetMapping("/del/{id}")
    public ResponseBean del(@PathVariable("id") Long id) {
        goodsService.del(id);
        ResponseBean res = new ResponseBean();
        res.setCode(AppEnum.ResultCode.success.v());
        return res;
    }
}
