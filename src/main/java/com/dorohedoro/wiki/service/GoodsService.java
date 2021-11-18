package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.*;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public PageBean<GoodsVO> getGoodsList(Goods reqBean) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if (!StringUtils.isEmpty(reqBean.getName())) {
            criteria.andNameLike("%" + reqBean.getName() + "%");
        }

        List<Long> categoryIdList = new ArrayList<>();
        CategoryExample categoryExample = new CategoryExample();
        Long categoryId = reqBean.getCategoryId();
        categoryExample.createCriteria().andParentIdEqualTo(categoryId);
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        if (!CollectionUtils.isEmpty(categoryList)) {
            //一级分类
            categoryIdList = categoryList.stream().map(Category::getId).collect(Collectors.toList());
        } else {
            //二级分类
            categoryIdList.add(categoryId);
        }
        criteria.andCategoryIdIn(categoryIdList);
        criteria.andDeletedEqualTo(AppEnum.YesOrNo.no.v());

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

