package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.Goods;
import com.dorohedoro.wiki.bean.GoodsExample;
import com.dorohedoro.wiki.bean.ResponseBean;
import com.dorohedoro.wiki.mapper.GoodsMapper;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.util.TimeUtil;
import com.dorohedoro.wiki.vo.GoodsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<GoodsVO> getGoodsList(Goods goods) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andNameLike("%" + goods.getName() + "%");
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);

        ArrayList<GoodsVO> goodsVOList = new ArrayList<>();
        for (Goods bean : goodsList) {
            GoodsVO goodsVO = new GoodsVO();
            BeanUtils.copyProperties(bean, goodsVO);
            goodsVOList.add(goodsVO);
        }
        
        return goodsVOList;
    }

}
