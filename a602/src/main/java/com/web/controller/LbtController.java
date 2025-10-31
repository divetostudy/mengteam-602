package com.web.controller;


import com.web.common.result.Result;
import com.web.pojo.VO.LbtVO;
import com.web.service.ILbtService;
import com.web.service.impl.LbtServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mai
 * @since 2024-06-02
 */
@RestController
@RequestMapping("/lbt")
public class LbtController {
    @Autowired
    private ILbtService iLbtService;

    @PostMapping("/addHome")
    @ApiOperation("上传首页轮播图")
    public Result<?> addHomeLbt(@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        iLbtService.addHomeLbt(image);
        return Result.success();
    }
    @GetMapping("/getHomeLbt")
    @ApiOperation("获取首页轮播图")
    public Result<List<LbtVO>> getHomeLbt(){
        List<String> photoUrl = iLbtService.getHomeLbt();
        List<LbtVO> list = new ArrayList<>();
        for (String url : photoUrl) {
            LbtVO lbtVO = new LbtVO();
            lbtVO.setPhoto(url);
            list.add(lbtVO);
        }
        return Result.success(list);
    }

    @DeleteMapping("/deleteLbt")
    public Result<?> deleteHomeLbt(@RequestParam(value = "url", required = false)String url) {
        iLbtService.deleteHomeLbt(url);
        return Result.success();
    }

    @PostMapping("/addCG")
    @ApiOperation("上传成果轮播图")
    public Result<?> addCGLbt(@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        iLbtService.addCGLbt(image);
        return Result.success();
    }
    @GetMapping("/getCGLbt")
    @ApiOperation("获取成果轮播图")
    public Result<List<LbtVO>> getCGLbt(){
        List<String> photoUrl = iLbtService.getCGLbt();
        List<LbtVO> list = new ArrayList<>();
        for (String url : photoUrl) {
            LbtVO lbtVO = new LbtVO();
            lbtVO.setPhoto(url);
            list.add(lbtVO);
        }
        return Result.success(list);
    }
}
