package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.domain.Category;
import com.dorohedoro.wiki.bean.domain.CategoryExample;
import com.dorohedoro.wiki.bean.vo.CategoryVO;
import com.dorohedoro.wiki.mapper.CategoryMapper;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.util.BeanUtil;
import com.dorohedoro.wiki.util.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 9:45c 
 */
@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMappper;

    public List<CategoryVO> getCategoryList() {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andDeletedEqualTo(AppEnum.YesOrNo.no.v());
        
        List<Category> categoryList = categoryMappper.selectByExample(categoryExample);
        List<CategoryVO> categoryVOList = BeanUtil.copyList(categoryList, CategoryVO.class);
        categoryVOList.sort(Comparator.comparing(CategoryVO::getSortFlag).reversed());
        return toTree(0L, categoryVOList);
    }

    public void addOrUpdCategory(Category categoryBO) {
        Long id = categoryBO.getId();
        if (id == null || id.equals(0L)) {
            categoryBO.setId(IDGenerator.nextId());
            categoryMappper.insertSelective(categoryBO);
        } else {
            categoryMappper.updateByPrimaryKeySelective(categoryBO);
        }
    }

    public void del(Long id) {
        Category category = new Category();
        category.setId(id);
        category.setDeleted(AppEnum.YesOrNo.yes.v());
        categoryMappper.updateByPrimaryKeySelective(category);
    }

    public List<CategoryVO> toTree(Long parentId, List<CategoryVO> allList) {
        if (CollectionUtils.isEmpty(allList)) {
            return null;
        }

        List<CategoryVO> res = new ArrayList<>();
        for (CategoryVO vo : allList) {
            if (vo.getParentId().equals(parentId)) {
                res.add(vo);

                List<CategoryVO> children = toTree(vo.getId(), allList);
                if (!CollectionUtils.isEmpty(children)) {
                    vo.setChildren(children);
                }
            }
        }
        return res;
    }
}

