package com.web.controller;


import com.web.common.result.Result;
import com.web.pojo.DO.Img;
import com.web.pojo.DO.ImgNews;
import com.web.pojo.DTO.NewsDTO;
import com.web.pojo.VO.NewsVO;
import com.web.pojo.VO.SelectNewsVO;
import com.web.pojo.VO.SelectStudentVO;
import com.web.service.INewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 新闻信息 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
@RestController
@RequestMapping("/news")
@Slf4j
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@Api(tags = "新闻接口")
public class NewsController {
    @Autowired
    private INewsService newsService;

    @PostMapping("/add")
    @ApiOperation("添加新闻")
    public Result add(@RequestParam(value = "files", required = false) MultipartFile[] files,
             @RequestParam(value = "image", required = false) MultipartFile image,
             @RequestParam(value = "title", required = false) String title,
             @RequestParam(value = "descr", required = false) String descr,
             @RequestParam(value = "category", required = false) String category,
             @RequestParam(value = "contect", required = false) String contect,
             @RequestParam(value = "time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) throws IOException {
        log.info("添加新闻");
        NewsVO newsVO = new NewsVO();
        newsVO.setTitle(title);
        newsVO.setDescr(descr);
        newsVO.setCategory(category);
        newsVO.setContect(contect);
        newsVO.setTime(time);
        newsService.add(files , newsVO, image);

        return  Result.success();
    }
    @GetMapping
    @ApiOperation("获取新闻")
    public Result<List<SelectNewsVO>> select(){
        log.info("获取所有新闻");
        List<SelectNewsVO> newsVOS = newsService.getnews();
        if (newsVOS == null){
            return Result.error("没有新闻");
        }
        return Result.success(newsVOS);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取新闻详情")
    public Result<List<SelectNewsVO>> selectNews(@PathVariable Integer id){
        List<SelectNewsVO> newsVOS = newsService.getnews(id);
        return Result.success(newsVOS);
    }

    @PostMapping("/update")
    @ApiOperation("修改新闻")
    public Result update
            (@RequestParam(value = "files",required = false) MultipartFile[] files,
             @RequestParam(value = "imgNews", required = false) ImgNews[] imgNews,
             @RequestParam(value = "coverUrl", required = false)String coverUrl,
             @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
             @RequestParam(value = "deleteUrl", required = false)String[] deleteUrl,
             @RequestParam(value = "id", required = false) Integer id,
             @RequestParam(value = "title", required = false) String title,
             @RequestParam(value = "descr", required = false) String descr,
             @RequestParam(value = "category", required = false) String category,
             @RequestParam(value = "contect", required = false) String contect,
             @RequestParam(value = "time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) throws IOException {
        log.info("修改新闻");
        NewsDTO newsDTO = new NewsDTO();
        Img img = new Img();
        //设置新闻封面
        img.setFile(coverImage);
        img.setUrl(coverUrl);
        //设置新闻信息
        newsDTO.setId(id);
        newsDTO.setTitle(title);
        newsDTO.setDescr(descr);
        newsDTO.setCategory(category);
        newsDTO.setContect(contect);
        newsDTO.setTime(time);
        newsService.update(files , newsDTO, img, imgNews, deleteUrl);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除新闻")
    public Result deleteNews(@PathVariable Integer id){
        log.info("删除新闻");
        newsService.delete(id);
        return Result.success();
    }

}
