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
        //name like ?
        if (!StringUtils.isEmpty(reqBean.getName())) {
            criteria.andNameLike("%" + reqBean.getName() + "%");
        }

        List<Long> categoryIdList = new ArrayList<>();
        CategoryExample categoryExample = new CategoryExample();
        //categoryId = ?
        Long categoryId = reqBean.getCategoryId();
        if (categoryId != null && !categoryId.equals(0L)) {
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
        }
        //deleted = 0
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

    public Long addOrUpdGoods(Goods reqBean) {
        Long id = reqBean.getId();
        Integer res = 0;
        if (id == null || id.equals(0)) {
            reqBean.setId(IDGenerator.nextId());
            res = goodsMapper.insertSelective(reqBean);
        } else {
            res = goodsMapper.updateByPrimaryKeySelective(reqBean);
        }

        if (res.equals(0)) {
            return AppEnum.ResultCode.db.v();
        } else {
            return reqBean.getId();
        }
    }

    public void del(Long id) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setDeleted(1L);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }
}

