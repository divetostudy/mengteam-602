package com.web.controller;


import com.web.common.result.Result;
import com.web.mapper.ActivitiesMapper;
import com.web.mapper.EnvironmentMapper;
import com.web.service.IActivitiesService;
import com.web.service.IEnvironmentService;
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
@RequestMapping("/activities")
public class ActivitiesController {
    @Autowired
    private IActivitiesService iActivitiesService;
    @Autowired
    private ActivitiesMapper activitiesMapper;
    @PostMapping("/add")
    @ApiOperation("上传团建活动图片")
    public Result<?> add(@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        iActivitiesService.add(image);
        return Result.success();
    }
    @GetMapping("/get")
    @ApiOperation("获取团建活动图片")
    public Result<?> getHomeLbt(){
        return Result.success(activitiesMapper.selectList(null));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除团建活动图片")
    public Result<?> delete(@RequestParam(value = "url", required = false)String url) {
        iActivitiesService.removeByUrl(url);
        return Result.success();
    }
}
