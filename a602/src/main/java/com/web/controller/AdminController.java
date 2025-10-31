package com.web.controller;

import com.web.common.result.Result;
import com.web.mapper.StudentMapper;
import com.web.mapper.TeacherMapper;
import com.web.pojo.DO.Student;
import com.web.pojo.DO.Teacher;
import com.web.pojo.DTO.StudentDTO;
import com.web.pojo.DTO.TeacherDTO;
import com.web.pojo.VO.StudentVO;
import com.web.pojo.VO.TeacherVO;
import com.web.properties.JwtProperties;
import com.web.service.StudentService;
import com.web.service.TeacherService;
import com.web.service.UserService;
import com.web.util.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "后台接口")
public class AdminController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/uploadTeacher")
    @ApiOperation("上传老师信息")
    public Result<String> uploadTeacher(@RequestParam("file") MultipartFile file,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "job", required = false) String job,
                                        @RequestParam(value = "introduction", required = false) String introduction,
                                        @RequestParam(value = "direction", required = false) String direction,
                                        @RequestParam(value = "honor", required = false) String honor) throws IOException {
        TeacherVO teacher = new TeacherVO();
        teacher.setName(name);
        teacher.setJob(job);
        teacher.setIntroduction(introduction);
        teacher.setHonor(honor);
        teacher.setDirection(direction);
        teacherService.upload(file,teacher);
        return Result.success();
    }

    @DeleteMapping("/deleteTeacher/{id}")
    @ApiOperation("删除老师信息")
    public Result<String> deleteTeacher(@PathVariable Long id){
        teacherService.delete(id);
        return Result.success("删除成功");
    }

//    @DeleteMapping("/deleteAllTeachers")
//    @ApiOperation("删除全部老师信息")
//    public Result<String> deleteAllTeachers() {
//        teacherMapper.delete((Long) null);
//        return Result.success("删除成功");
//    }


    @PostMapping ("/updateTeacher")
    @ApiOperation("修改老师信息")
    public Result<String> updateTeacher(MultipartFile file,
                                        @RequestParam(value = "id", required = false) Long id,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "job", required = false) String job,
                                        @RequestParam(value = "introduction", required = false) String introduction,
                                        @RequestParam(value = "direction", required = false) String direction,
                                        @RequestParam(value = "honor", required = false) String honor) throws IOException {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(id);
        teacherDTO.setName(name);
        teacherDTO.setJob(job);
        teacherDTO.setIntroduction(introduction);
        teacherDTO.setHonor(honor);
        teacherDTO.setDirection(direction);
        teacherService.updateTeacher(file,teacherDTO);
        return Result.success();
    }


    @PostMapping("/uploadStudent")
    @ApiOperation("上传学生信息")
    public Result uploadStudent(@RequestParam("file") MultipartFile file ,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value = "contact", required = false) String contact,
                                @RequestParam(value = "graduationYear", required = false) String graduationYear,
                                @RequestParam(value = "lifeMotto", required = false) String lifeMotto,
                                @RequestParam(value = "awardAtschool", required = false) String awardAtschool,
                                @RequestParam(value = "say", required = false) String say,
                                @RequestParam(value = "major", required = false) String major,
                                @RequestParam(value = "workExperience", required = false) String workExperience,
                                @RequestParam(value = "hobby", required = false) String hobby,
                                @RequestParam(value = "introduction", required = false) String introduction) throws IOException{
        StudentVO student = new StudentVO();
        student.setName(name);
        student.setGender(gender);
        student.setContact(contact);
        student.setGraduationYear(graduationYear);
        student.setLifeMotto(lifeMotto);
        student.setAwardAtschool(awardAtschool);
        student.setSay(say);
        student.setMajor(major);
        student.setWorkExperience(workExperience);
        student.setHobby(hobby);
        student.setIntroduction(introduction);
        studentService.uploadStudent(file,student);
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
                                @RequestParam(value = "lifeMotto", required = false) String lifeMotto,
                                @RequestParam(value = "awardAtschool", required = false) String awardAtschool,
                                @RequestParam(value = "say", required = false) String say,
                                @RequestParam(value = "major", required = false) String major,
                                @RequestParam(value = "workExperience", required = false) String workExperience,
                                @RequestParam(value = "hobby", required = false) String hobby,
                                @RequestParam(value = "introduction", required = false) String introduction) throws IOException {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(name);
        studentDTO.setId(id);
        studentDTO.setGender(gender);
        studentDTO.setContact(contact);
        studentDTO.setGraduationYear(graduationYear);
        studentDTO.setLifeMotto(lifeMotto);
        studentDTO.setAwardAtschool(awardAtschool);
        studentDTO.setSay(say);
        studentDTO.setMajor(major);
        studentDTO.setHobby(hobby);
        studentDTO.setIntroduction(introduction);
        studentDTO.setWorkExperience(workExperience);
        studentService.update(file , studentDTO);
        return Result.success();
    }

    @DeleteMapping("/deleteStudent/{id}")
    @ApiOperation("删除学生信息")
    public Result<String> deleteStudent(@PathVariable Long id){
        studentService.deleteById(id);
       // studentMapper.deleteById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/getStudent")
    @ApiOperation("获取审核学生信息")
    public Result<List<Student>> getStudent(){
        List<Student> students = studentMapper.selectPre();
        return Result.success(students);
    }

    @PostMapping("/addPreStudent")
    @ApiOperation("添加审核学生")
    public Result<?> addPreStudent(@RequestParam(value = "id", required = false) Long id){
        Student student =  studentMapper.selectOnePre(id);
        studentMapper.insert(student);
        studentMapper.deleteOnePre(id);
        return Result.success("添加成功");
    }
    @DeleteMapping("/deletePreStudent/{id}")
    @ApiOperation("删除审核学生")
    public Result<?> deletePreStudent(@PathVariable Long id){
        String originalUrl = studentMapper.getPrePhotoUrl(id);
        aliOssUtil.deleteObjectByUrl("student", originalUrl);
        studentMapper.deleteOnePre(id);

        return Result.success("删除成功");
    }
}
