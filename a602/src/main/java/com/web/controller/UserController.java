package com.web.controller;

import com.web.common.result.Result;
import com.web.mapper.StudentMapper;
import com.web.mapper.UserMapper;
import com.web.pojo.DTO.StudentDTO;
import com.web.pojo.VO.SelectStudentVO;
import com.web.pojo.VO.StudentVO;
import com.web.properties.JwtProperties;
import com.web.service.StudentService;
import com.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "学生用户接口")
@Slf4j
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/uploadStudent")
    @ApiOperation("上传学生信息")
    public Result uploadStudent(@RequestParam(value = "file", required = false) MultipartFile file ,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value = "contact", required = false) String contact,
                                @RequestParam(value = "graduationYear", required = false) String graduationYear,
                                @RequestParam(value = "hobby", required = false) String hobby,
                                @RequestParam(value = "introduction", required = false) String introduction,
                                @RequestParam(value = "lifeMotto", required = false) String lifeMotto,
                                @RequestParam(value = "awardAtschool", required = false) String awardAtschool,
                                @RequestParam(value = "say", required = false) String say,
                                @RequestParam(value = "major", required = false) String major,
                                @RequestParam(value = "workExperience", required = false) String workExperience) throws IOException {
        StudentVO student = new StudentVO();
        student.setName(name);
        student.setGender(gender);
        student.setContact(contact);
        student.setGraduationYear(graduationYear);
        student.setLifeMotto(lifeMotto);
        student.setAwardAtschool(awardAtschool);
        student.setSay(say);
        student.setHobby(hobby);
        student.setIntroduction(introduction);
        student.setMajor(major);
        student.setWorkExperience(workExperience);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("token");
        log.info(token);
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
        log.info("claims:{}",claims);
        String uuid = claims.get("exp").toString();
        studentService.uploadStudent1(file,student,uuid);
        return Result.success();
    }
    @PostMapping("/updateStudent")
    @ApiOperation("修改学生信息")
    public Result updateStudent(MultipartFile file ,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "id", required = false) Long id,
                                @RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value = "contact", required = false) String contact,
                                @RequestParam(value = "graduationYear", required = false) String graduationYear,
                                @RequestParam(value = "hobby", required = false) String hobby,
                                @RequestParam(value = "introduction", required = false) String introduction,
                                @RequestParam(value = "lifeMotto", required = false) String lifeMotto,
                                @RequestParam(value = "awardAtschool", required = false) String awardAtschool,
                                @RequestParam(value = "say", required = false) String say,
                                @RequestParam(value = "major", required = false) String major,
                                @RequestParam(value = "workExperience", required = false) String workExperience) throws IOException {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(name);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
        log.info("claims:{}",claims);
        String uuid = claims.get("exp").toString();
        studentDTO.setId(id);
        studentDTO.setGender(gender);
        studentDTO.setContact(contact);
        studentDTO.setGraduationYear(graduationYear);
        studentDTO.setLifeMotto(lifeMotto);
        studentDTO.setAwardAtschool(awardAtschool);
        studentDTO.setSay(say);
        studentDTO.setMajor(major);
        studentDTO.setWorkExperience(workExperience);
        studentDTO.setHobby(hobby);
        studentDTO.setIntroduction(introduction);
        studentService.update1(file,studentDTO,uuid);
        return Result.success();
    }

    @GetMapping()
    @ApiModelProperty("获取学生信息")
    public Result<?> getStudent() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
        log.info("claims:{}",claims);
        String uuid = claims.get("exp").toString();
        List<SelectStudentVO> student = studentMapper.getStudent(uuid);
        if (student.isEmpty()){
            return Result.error("该用户未上传学生信息");
        }
        return Result.success(student);

    }

}
