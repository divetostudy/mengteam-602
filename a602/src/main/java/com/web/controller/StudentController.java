package com.web.controller;


import com.web.mapper.StudentMapper;
import com.web.pojo.VO.SelectStudentVO;
import com.web.pojo.VO.StudentVO;
import com.web.common.result.Result;
import com.web.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Api(tags = "学生信息接口")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;


    @GetMapping()
    @ApiOperation("获取学生信息")
    public Result<List<SelectStudentVO>> getStudents() {
        log.info("获取学生信息");
        List<SelectStudentVO> StudentVO = studentService.getStudent();
        if (StudentVO.isEmpty()){
            return Result.error("没有学生信息");
        }
        return Result.success(StudentVO,"获取成功");
    }

    @GetMapping("/{name}")
    @ApiOperation("获取学生详细信息")
    public Result<List<StudentVO>> getStudents(@PathVariable String name) {
        log.info("获取学生详细信息");
        List<StudentVO> studentVOS = studentService.getStudentByName(name);
        if (studentVOS.isEmpty()){
            return Result.error("没有学生信息");
        }
        return Result.success(studentVOS,"获取成功");
    }




    //    @GetMapping("/teachers")
//    @ApiOperation("分页获取学生信息")
////    public List<Teacher> getTeachers(@RequestParam(defaultValue = "1") int pageNum,
////                                     @RequestParam(defaultValue = "3") int pageSize) {
////        return teacherService.findAllTeachers(pageNum, pageSize);
////    }
//    public Result<List<Teacher>> getTeachers(@RequestParam int pageNum,
//                                     @RequestParam int pageSize) {
//        return Result.success(teacherService.findAllTeachers(pageNum, pageSize));
//    }





    @DeleteMapping("/delete")
    @ApiOperation("批量删除学生信息")
    public Result<String> deleteTeachers(@RequestBody List<Long> ids) {
        studentService.deleteStudentsByIds(ids);
        return Result.success("删除成功");
    }

}
