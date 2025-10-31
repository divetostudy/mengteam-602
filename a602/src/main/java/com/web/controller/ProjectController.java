package com.web.controller;


import com.web.common.result.Result;
import com.web.pojo.DO.Img;
import com.web.pojo.DO.ImgNews;
import com.web.pojo.DO.ImgProject;
import com.web.pojo.DTO.ProjectDTO;
import com.web.pojo.VO.ProjectVO;
import com.web.pojo.VO.SelectProjectVO;
import com.web.service.IProjectService;
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
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-15
 */
@RestController
@RequestMapping("/project")
@Api(tags = "项目相关接口")
@Slf4j
public class ProjectController {
    @Autowired
    IProjectService projectService;

    @GetMapping
    @ApiOperation("获取项目")
    public Result<List<SelectProjectVO>> getProject(){
        log.info("获取所有项目");
        List<SelectProjectVO> selectProjectVOList = projectService.get();
        return Result.success(selectProjectVOList);
    }

    @PostMapping("/add")
    @ApiOperation("新增项目")
    public Result add(@RequestParam(value = "files", required = false) MultipartFile[] files,
                      @RequestParam(value = "image", required = false) MultipartFile image,
                      @RequestParam(value = "title", required = false) String title,
                      @RequestParam(value = "content", required = false) String content) throws IOException {
        log.info("新增项目");
        ProjectVO projectVO = new ProjectVO();
        projectVO.setTitle(title);
        projectVO.setContent(content);
        projectService.add(files,image,projectVO);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改项目")
    public  Result update(@RequestParam(value = "files",required = false) MultipartFile[] files,
                          @RequestParam(value = "imgProjects", required = false) ImgProject[] imgProjects,
                          @RequestParam(value = "coverUrl", required = false)String coverUrl,
                          @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
                          @RequestParam(value = "deleteUrl", required = false)String[] deleteUrl,
                          @RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "content", required = false) String content) throws IOException {
        log.info("修改项目");
        Img image = new Img();
        //设置项目封面
        image.setUrl(coverUrl);
        image.setFile(coverImage);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(id);
        projectDTO.setTitle(title);
        projectDTO.setContent(content);
        projectService.update(files , image , projectDTO , imgProjects , deleteUrl);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除项目")
    public Result delete(@PathVariable Integer id){
        log.info("删除项目");
        projectService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取项目详情")
    public Result<List<SelectProjectVO>> selectProject(@PathVariable Integer id){
        log.info("获取项目详情");
        List<SelectProjectVO> selectProjectVOList = projectService.select(id);
        return Result.success(selectProjectVOList);
    }

}
