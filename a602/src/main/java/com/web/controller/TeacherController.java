package com.web.controller;


import com.web.mapper.TeacherMapper;
import com.web.pojo.VO.SelectTeacherVO;
import com.web.common.result.Result;
import com.web.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@Api(tags = "老师信息接口")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;

    @GetMapping()
    @ApiOperation("获取老师信息")
    public Result<List<SelectTeacherVO>> getTeachers() {
        log.info("获取老师信息");

        List<SelectTeacherVO> selectTeacherVOS = teacherService.getTeachers();
        if (selectTeacherVOS.isEmpty()) {
            return Result.error("没有老师信息");
        }
        return Result.success(selectTeacherVOS,"获取成功");

    }

//    @GetMapping(value ="/{name}")
//    @ApiOperation("根据名字获取老师图片")
//    public ResponseEntity<Resource> getTeacherImage(@PathVariable String name) throws IOException {
//        String decodedName = URLDecoder.decode(name, "UTF-8");
//        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
//        wrapper.like("name",decodedName);
//        Teacher teacher = teacherMapper.selectOne(wrapper);
//
//        String imageUrl = teacher.getPhotoUrl();
//
//        byte[] imageData = Files.readAllBytes(Path.of(imageUrl));
//
//        // 使用字节数组创建ByteArrayResource
//        ByteArrayResource resource = new ByteArrayResource(imageData);
//        System.out.println(resource);
//        // 获取文件的MIME类型
//        String contentType = Files.probeContentType(Path.of(imageUrl));
//        System.out.println(contentType);
//        // 如果无法确定文件的MIME类型，则默认使用APPLICATION_OCTET_STREAM
//        MediaType mediaType = contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM;
//
//        return ResponseEntity.ok()
//                .contentType(mediaType)
//                .body(resource);
//    }



  /*  @PostMapping("/update")
    @ApiOperation("修改老师信息")
    public Result<String> updateTeacher(@RequestBody TeacherVO teacherVO){
        teacherService.updateTeacher(teacherVO);
        return Result.success();
    }*/

//    @GetMapping("/teachers")
//    @ApiOperation("分页获取老师信息")
////    public List<Teacher> getTeachers(@RequestParam(defaultValue = "1") int pageNum,
////                                     @RequestParam(defaultValue = "3") int pageSize) {
////        return teacherService.findAllTeachers(pageNum, pageSize);
////    }
//    public Result<List<Teacher>> getTeachers(@RequestParam int pageNum,
//                                     @RequestParam int pageSize) {
//        return Result.success(teacherService.findAllTeachers(pageNum, pageSize));
//    }





    @DeleteMapping("/delete")
    @ApiOperation("批量删除老师信息")
    public Result<String> deleteTeachers(@RequestBody List<Long> ids) {
        teacherService.deleteTeachersByIds(ids);
        return Result.success("删除成功");
    }

}
