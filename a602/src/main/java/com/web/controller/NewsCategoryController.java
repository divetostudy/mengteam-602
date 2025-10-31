package com.web.controller;


import com.web.common.result.Result;
import com.web.pojo.DO.NewsCategory;
import com.web.service.INewsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 新闻分类 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
@RestController
@RequestMapping("/news/category")
@Slf4j
@Api(tags = "新闻分类接口")
public class NewsCategoryController {
    @Autowired
    private INewsCategoryService newsCategoryService;
    @GetMapping("/list")
    @ApiModelProperty("查询分类")
    public Result<List<NewsCategory>> list(){
        List<NewsCategory> newsCategories= newsCategoryService.find();
        return Result.success(newsCategories);
    }

}
