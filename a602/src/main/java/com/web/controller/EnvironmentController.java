package com.web.controller;


import com.web.common.result.Result;
import com.web.mapper.EnvironmentMapper;
import com.web.pojo.VO.LbtVO;
import com.web.service.IEnvironmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mai
 * @since 2024-09-26
 */
@RestController
@RequestMapping("/environment")
public class EnvironmentController {

    @Autowired
    private IEnvironmentService  iEnvironmentService;
    @Autowired
    private EnvironmentMapper environmentMapper;
    @PostMapping("/add")
    @ApiOperation("上传实验室环境图片")
    public Result<?> add(@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        iEnvironmentService.add(image);
        return Result.success();
    }
    @GetMapping("/get")
    @ApiOperation("获取实验室环境图片")
    public Result<?> getHomeLbt(){
       return Result.success(environmentMapper.selectList(null));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除实验室环境图片")
    public Result<?> delete(@RequestParam(value = "url", required = false)String url) {
        iEnvironmentService.removeByUrl(url);
        return Result.success();
    }
}
