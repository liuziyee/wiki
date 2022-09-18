package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.domain.Category;
import com.dorohedoro.wiki.bean.domain.CategoryExample;
import com.dorohedoro.wiki.bean.domain.Goods;
import com.dorohedoro.wiki.bean.domain.GoodsExample;
import com.dorohedoro.wiki.bean.vo.GoodsVO;
import com.dorohedoro.wiki.bean.vo.PageBean;
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
        String name = goodsBO.getName();
        Long id = goodsBO.getId();
        Long categoryId = goodsBO.getCategoryId();
        
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        
        if (id != null) {
            criteria.andIdEqualTo(id);
        }

        List<Long> categoryIdList = new ArrayList<>();
        CategoryExample categoryExample = new CategoryExample();
        
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
        
        criteria.andDeletedEqualTo(AppEnum.YesOrNo.no.v());

        PageHelper.startPage(goodsBO.getPage(), goodsBO.getSize());
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        goodsList.sort(Comparator.comparing(Goods::getUpdateTime).reversed());
        
        List<GoodsVO> goodsVOList = BeanUtil.copyList(goodsList, GoodsVO.class);
        categoryExample.clear();

        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        PageBean<GoodsVO> pageBean = new PageBean<>();
        pageBean.setList(goodsVOList);
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }

    public void addOrUpdGoods(Goods goodsBO) {
        Long id = goodsBO.getId();
        Integer res = 0;
        if (id == null || id.equals(0L)) {
            goodsBO.setId(IDGenerator.nextId());
            goodsMapper.insertSelective(goodsBO);
        } else {
            goodsMapper.updateByPrimaryKeySelective(goodsBO);
        }
    }

    public void del(Long id) {
        Goods goods = new Goods();
        goods.setId(id);
        goods.setDeleted(1L);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }
}

