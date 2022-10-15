package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.domain.Category;
import com.dorohedoro.wiki.bean.vo.CategoryVO;
import com.dorohedoro.wiki.bean.vo.ResponseBean;
import com.dorohedoro.wiki.service.CategoryService;
import com.dorohedoro.wiki.util.ResCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController{
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/tree")
    public ResponseBean getCategoryList() {
        List<CategoryVO> categoryVOList = categoryService.getCategoryList();

        ResponseBean<List<CategoryVO>> res = new ResponseBean<>();
        res.setCode(ResCode.success.getCode());
        res.setData(categoryVOList);
        return res;
    }

    @PostMapping("/addOrUpd")
    public ResponseBean addOrUpdCategory(@RequestBody Category categoryBO) {
        ResponseBean res = new ResponseBean();
        categoryService.addOrUpdCategory(categoryBO);
        res.setCode(ResCode.success.getCode());
        return res;
    }

    @GetMapping("/del/{id}")
    public ResponseBean del(@PathVariable Long id) {
        categoryService.del(id);
        ResponseBean res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        return res;
    }
    
    
}
