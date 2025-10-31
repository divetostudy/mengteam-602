package com.web.controller;


import com.web.common.result.Result;
import com.web.mapper.ActivitiesMapper;
import com.web.mapper.FoodMapper;
import com.web.service.IActivitiesService;
import com.web.service.IFoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mai
 * @since 2024-09-26
 */
@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private IFoodService iFoodService;
    @Autowired
    private FoodMapper foodMapper;
    @PostMapping("/add")
    @ApiOperation("上传美食分享图片")
    public Result<?> add(@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        iFoodService.add(image);
        return Result.success();
    }
    @GetMapping("/get")
    @ApiOperation("获取美食分享图片")
    public Result<?> getHomeLbt(){
        return Result.success(foodMapper.selectList(null));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除美食分享图片")
    public Result<?> delete(@RequestParam(value = "url", required = false)String url) {
        iFoodService.removeByUrl(url);
        return Result.success();
    }
}
