package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.bean.GoodsExample;
import com.dorohedoro.wiki.mapper.GoodsMapper;
import com.dorohedoro.wiki.util.BeanUtil;
import com.dorohedoro.wiki.bean.vo.GoodsVO;
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
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<GoodsVO> getGoodsList(Goods goods) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (!ObjectUtils.isEmpty(goods.getName())) {
            criteria.andNameLike("%" + goods.getName() + "%");
        }
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);

        List<GoodsVO> goodsVOList = BeanUtil.copyList(goodsList, GoodsVO.class);
        return goodsVOList;
    }

}
