package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.bean.GoodsExample;
import com.dorohedoro.wiki.bean.PageBean;
import com.dorohedoro.wiki.mapper.GoodsMapper;
import com.dorohedoro.wiki.util.BeanUtil;
import com.dorohedoro.wiki.bean.GoodsVO;
import com.dorohedoro.wiki.util.IDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 9:45c 
 */
@Service
@Slf4j
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public PageBean<GoodsVO> getGoodsList(Goods reqBean) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (!ObjectUtils.isEmpty(reqBean.getName())) {
            criteria.andNameLike("%" + reqBean.getName() + "%");
        }
        criteria.andDeletedEqualTo(0L);
        
        PageHelper.startPage(reqBean.getPage(), reqBean.getSize());
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);

        List<GoodsVO> goodsVOList = BeanUtil.copyList(goodsList, GoodsVO.class);
        PageBean<GoodsVO> pageBean = new PageBean<>();
        pageBean.setList(goodsVOList);
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }

    public void addOrUpdGoods(Goods reqBean) {
        Long id = reqBean.getId();
        if (id == null || id.equals(0)) {
            reqBean.setId(IDGenerator.nextId());
            System.out.println(IDGenerator.nextId());
            goodsMapper.insertSelective(reqBean);
        } else {
            goodsMapper.updateByPrimaryKeySelective(reqBean);
        }
    }

    public void del(Long id) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setDeleted(1L);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }
}

