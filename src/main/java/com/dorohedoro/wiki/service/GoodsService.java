package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.VO.GoodsVO;
import com.dorohedoro.wiki.bean.VO.PageBean;
import com.dorohedoro.wiki.bean.domain.Category;
import com.dorohedoro.wiki.bean.domain.CategoryExample;
import com.dorohedoro.wiki.bean.domain.Goods;
import com.dorohedoro.wiki.bean.domain.GoodsExample;
import com.dorohedoro.wiki.mapper.CategoryMapper;
import com.dorohedoro.wiki.mapper.GoodsMapper;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.util.BeanUtil;
import com.dorohedoro.wiki.util.IDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryMapper categoryMapper;

    public PageBean<GoodsVO> getGoodsList(Goods goodsBO) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        //name like ?
        if (!StringUtils.isEmpty(goodsBO.getName())) {
            criteria.andNameLike("%" + goodsBO.getName() + "%");
        }

        List<Long> categoryIdList = new ArrayList<>();
        CategoryExample categoryExample = new CategoryExample();
        //categoryId = ?
        Long categoryId = goodsBO.getCategoryId();
        if (categoryId != null && !categoryId.equals(0L)) {
            categoryExample.createCriteria().andParentIdEqualTo(categoryId);
            List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
            if (!CollectionUtils.isEmpty(categoryList)) {
                categoryIdList = categoryList.stream().map(Category::getId).collect(Collectors.toList());
            } else {
                categoryIdList.add(categoryId);
            }
            criteria.andCategoryIdIn(categoryIdList);
        }
        //deleted = 0
        criteria.andDeletedEqualTo(AppEnum.YesOrNo.no.v());

        PageHelper.startPage(goodsBO.getPage(), goodsBO.getSize());
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        goodsList.sort(Comparator.comparing(Goods::getUpdateTime).reversed());
        
        List<GoodsVO> goodsVOList = BeanUtil.copyList(goodsList, GoodsVO.class);
        categoryExample.clear(); //reset Criteria
        //goodsVOList.stream().forEach(o -> );

        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        PageBean<GoodsVO> pageBean = new PageBean<>();
        pageBean.setList(goodsVOList);
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }

    public Long addOrUpdGoods(Goods goodsBO) {
        Long id = goodsBO.getId();
        Integer res = 0;
        if (id == null || id.equals(0)) {
            goodsBO.setId(IDGenerator.nextId());
            res = goodsMapper.insertSelective(goodsBO);
        } else {
            res = goodsMapper.updateByPrimaryKeySelective(goodsBO);
        }

        if (res.equals(0)) {
            return AppEnum.ResultCode.db.v();
        } else {
            return goodsBO.getId();
        }
    }

    public void del(Long id) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setDeleted(1L);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }
}

