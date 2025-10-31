package com.web.controller;


import com.web.common.result.Result;
import com.web.pojo.DO.Achievement;
import com.web.pojo.DO.ImgAchievement;
import com.web.pojo.DTO.AchievementDTO;
import com.web.pojo.VO.AchievementVO;
import com.web.pojo.VO.SelectAchievementVO;
import com.web.service.IAchievementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 成果 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-13
 */
@RestController
@RequestMapping("/achievement")
@Api(tags = " 成果展示接口")
@Slf4j
public class AchievementController {

    @Autowired
    private IAchievementService achievementService;

    @GetMapping
    @ApiOperation("获取成果")
    public Result<List<SelectAchievementVO>> select(){
        log.info("获取所有成果");
        List<SelectAchievementVO> achievementVOS = achievementService.select();
        return Result.success(achievementVOS);
    }

    @PostMapping("/add")
    @ApiOperation("新增成果")
    public Result add
            (@RequestParam(value = "files", required = false) MultipartFile[] files,
             @RequestParam(value = "title", required = false) String title,
             @RequestParam(value = "content", required = false) String content) throws IOException {
        log.info("新增成果");
        AchievementVO achievementVO = new AchievementVO();
        achievementVO.setTitle(title);
        achievementVO.setContent(content);
        achievementService.add(files , achievementVO);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改成果")
    public Result update
            (@RequestParam(value = "files", required = false) MultipartFile[] files,
             @RequestParam(value = "deleteUrl", required = false)String[] deleteUrl,
             @RequestParam(value = "imgAchievement", required = false) ImgAchievement[] imgAchievements,
             @RequestParam(value = "id", required = false) Integer id,
             @RequestParam(value = "title", required = false) String title,
             @RequestParam(value = "content", required = false) String content) throws IOException {
        log.info("修改成果");
        AchievementDTO achievementDTO = new AchievementDTO();
        achievementDTO.setId(id);
        achievementDTO.setTitle(title);
        achievementDTO.setContent(content);
        achievementService.update(achievementDTO, files , deleteUrl , imgAchievements);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除成果")
    public Result delete(@PathVariable Integer id){
        log.info("删除成果");
        achievementService.delete(id);
        return Result.success();
    }

}
