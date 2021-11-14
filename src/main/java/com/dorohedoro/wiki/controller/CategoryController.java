package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.*;
import com.dorohedoro.wiki.service.CategoryService;
import com.dorohedoro.wiki.service.CategoryService;
import com.dorohedoro.wiki.util.AppEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 9:45
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/list")
    public ResponseBean getCategoryList() {
        List<CategoryVO> categoryVOList = categoryService.getCategoryList();

        ResponseBean<List<CategoryVO>> res = new ResponseBean<>();
        res.setCode(AppEnum.ResultCode.success.v());
        res.setData(categoryVOList);
        return res;
    }

    @PostMapping("/addOrUpd")
    public ResponseBean addOrUpdCategory(@RequestBody Category reqBean) {
        ResponseBean res = new ResponseBean();
        categoryService.addOrUpdCategory(reqBean);
        res.setCode(AppEnum.ResultCode.success.v());
        return res;
    }

    @GetMapping("/del/{id}")
    public ResponseBean del(@PathVariable("id") Long id) {
        categoryService.del(id);
        ResponseBean res = new ResponseBean();
        res.setCode(AppEnum.ResultCode.success.v());
        return res;
    }
}
