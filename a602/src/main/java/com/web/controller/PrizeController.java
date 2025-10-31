package com.web.controller;


import com.web.common.result.Result;
import com.web.pojo.DO.Img;
import com.web.pojo.DTO.PrizeDTO;
import com.web.pojo.VO.PrizeVO;
import com.web.service.IPrizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 奖项 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-16
 */
@RestController
@RequestMapping("/prize")
@Api(tags = "奖项相关接口")
@Slf4j
public class PrizeController {
    @Autowired
    private IPrizeService prizeService;

    @GetMapping
    @ApiOperation("获取所有奖项")
    public Result<List<PrizeVO>> getPrize(){
        log.info("获取所有奖项");
        List<PrizeVO> prizeVOList = prizeService.getPrize();
        return Result.success(prizeVOList);
    }

    @PostMapping("/add")
    @ApiOperation("新增奖项")
    public Result add(@RequestParam(value = "file", required = false) MultipartFile file,
                      @RequestParam(value = "content", required = false) String content) throws IOException {
        log.info("新增奖项");
        PrizeDTO prizeDTO = new PrizeDTO();
        prizeDTO.setContent(content);
        prizeService.add(prizeDTO,file);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改奖项")
    public Result update(@RequestParam(value = "url", required = false)String url,
                         @RequestParam(value = "file" , required = false)  MultipartFile file,
                         @RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "content", required = false) String content) throws IOException {
        log.info("修改奖项");
        Img img = new Img();
        img.setUrl(url);
        img.setFile(file);
        PrizeDTO prizeDTO = new PrizeDTO();
        prizeDTO.setId(id);
        prizeDTO.setContent(content);//设置描述

        prizeService.update(prizeDTO , img);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除奖项")
    public Result delete(@PathVariable Integer id){
        log.info("删除奖项");
        prizeService.delete(id);
        return Result.success();
    }

}
