package com.web.controller;

import com.web.common.result.Result;
import com.web.mapper.UserMapper;
import com.web.properties.JwtProperties;
import com.web.util.AliOssUtil;
import com.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
@RestController
@RequestMapping("/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<?> login(String username,String password){
        log.info("用户登录：{}",username);
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        String checkPassword = userMapper.getPassword(username);
        if(!checkPassword.equals(password)){
            return Result.error("密码错误");
        }
        if (username.equals("admin")){
            claims.put("admin",username);
            String jwt = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);
            map.put("admin",jwt);
        }
        if (username.equals("ZSA602")){
            claims.put("user",username);
            String jwt = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);
            map.put("user",jwt);
        }

        return Result.success(map);
      //  return Result.success(result);

    }


    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/achievement")
    @ApiOperation("文件上传")
    public Result<String> achievement(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //上传的原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //新文件名称
            String objectName = UUID.randomUUID().toString() + extension;


            String filePath = aliOssUtil.upload(file.getBytes(), "achievement", objectName);
            return Result.success(filePath);

        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return Result.error("文件上传失败");
    }


    @PostMapping("/news")
    @ApiOperation("文件删除")
    public Result<String> upload(String url){
        log.info("文件删除：{}",url);

        aliOssUtil.deleteObjectByUrl("achievement",url);

        return Result.success();

    }



    @PostMapping("/prize")
    @ApiOperation("文件上传")
    public Result<String> upload1(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //上传的原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //新文件名称
            String objectName = UUID.randomUUID().toString() + extension;


            String filePath = aliOssUtil.upload(file.getBytes(), "prize", objectName);
            return Result.success(filePath);

        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return Result.error("文件上传失败");
    }



    @PostMapping("/project")
    @ApiOperation("文件上传")
    public Result<String> upload2(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //上传的原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //新文件名称
            String objectName = UUID.randomUUID().toString() + extension;


            String filePath = aliOssUtil.upload(file.getBytes(), "project", objectName);
            return Result.success(filePath);

        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return Result.error("文件上传失败");
    }



    @PostMapping("/student")
    @ApiOperation("文件上传")
    public Result<String> upload3(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //上传的原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //新文件名称
            String objectName = UUID.randomUUID().toString() + extension;


            String filePath = aliOssUtil.upload(file.getBytes(), "student", objectName);
            return Result.success(filePath);

        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return Result.error("文件上传失败");
    }



    @PostMapping("/teacher")
    @ApiOperation("文件上传")
    public Result<String> upload4(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //上传的原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //新文件名称
            String objectName = UUID.randomUUID().toString() + extension;


            String filePath = aliOssUtil.upload(file.getBytes(), "teacher", objectName);
            return Result.success(filePath);

        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return Result.error("文件上传失败");
    }



}
