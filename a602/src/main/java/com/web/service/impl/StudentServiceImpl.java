package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.web.constant.PhotoConstant;
import com.web.mapper.StudentMapper;
import com.web.pojo.DO.Student;
import com.web.pojo.DTO.StudentDTO;
import com.web.pojo.VO.SelectStudentVO;
import com.web.pojo.VO.StudentVO;
import com.web.service.StudentService;
import com.web.common.util.FileNameUtils;
import com.web.util.AliOssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private PhotoConstant photoConstant;

    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * 获取学生信息
     *
     * @return
     */
    @Override
    public List<SelectStudentVO> getStudent() {
        // 从数据库中查询学生列表
        List<Student> students = studentMapper.selectList(null);
        // 创建学生视图对象列表
        List<SelectStudentVO> studentVOList = new ArrayList<>();

        for (Student student : students) {
            SelectStudentVO studentVO = new SelectStudentVO();
            // 复制属性从Student到StudentVO
            BeanUtils.copyProperties(student, studentVO);
            // 获取学生图片的URL
            String imageUrl = student.getPhotoUrl();

//            // 使用Optional，如果imageUrl不为空，则尝试读取图片数据
//            Optional.ofNullable(imageUrl)
//                    .ifPresent(url -> {
//                        try {
//                            // 读取图片文件的所有字节
//                            byte[] imageData = Files.readAllBytes(Path.of(url));
//                            // 将图片数据设置到StudentVO中
//                            studentVO.setPhoto(imageData);
//                        } catch (IOException e) {
//                            // 如果发生错误，抛出RuntimeException
//                            throw new RuntimeException(e);
//                        }
//                    });

            // 将学生视图对象添加到列表中
            studentVOList.add(studentVO);
        }

        // 返回学生视图对象列表
        return studentVOList;
    }

    @Override
    public List<StudentVO> getStudentByName(String name) {
        /*QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);*/
        List<Student> students = studentMapper.selectByName(name);

        List<StudentVO> studentVOList = new ArrayList<>();
        for (Student student : students) {
            StudentVO studentVO = new StudentVO();
            // 复制属性从Student到StudentVO
            BeanUtils.copyProperties(student, studentVO);
            // 获取学生图片的URL
//            String imageUrl = student.getPhotoUrl();
//
//            // 使用Optional，如果imageUrl不为空，则尝试读取图片数据
//            Optional.ofNullable(imageUrl)
//                    .ifPresent(url -> {
//                        try {
//                            // 读取图片文件的所有字节
//                            byte[] imageData = Files.readAllBytes(Path.of(url));
//                            // 将图片数据设置到StudentVO中
//                            studentVO.setPhoto(imageData);
//                        } catch (IOException e) {
//                            // 如果发生错误，抛出RuntimeException
//                            throw new RuntimeException(e);
//                        }
//                    });

            // 将学生视图对象添加到列表中
            studentVOList.add(studentVO);
        }

        // 返回学生视图对象列表
        return studentVOList;

    }

    @Override
    public void update(MultipartFile file, StudentDTO student) throws IOException {
        if (file != null) {
            String objectName = aliOssUtil.getObjectName(file);
            String Url = aliOssUtil.upload(file.getBytes(), "student",objectName);
            //String Url = photoConstant.saveFile(file , "student");
            String originalUrl = studentMapper.getPhotoUrl(student.getId());

            if (originalUrl != null) {//删除原文件
                aliOssUtil.deleteObjectByUrl("student", originalUrl);
//                File path = new File(originalUrl);
//                path.delete();
            }
            student.setPhotoUrl(Url);
        }else {
            student.setPhotoUrl(null);
        }

        studentMapper.update(student);
    }
    @Override
    public void update1(MultipartFile file, StudentDTO student,String uuid) throws IOException {
        if (file != null) {
            String objectName = aliOssUtil.getObjectName(file);
            String Url = aliOssUtil.upload(file.getBytes(), "student",objectName);
            //String Url = photoConstant.saveFile(file , "student");
            String originalUrl = studentMapper.getPhotoUrl(student.getId());

            if (originalUrl != null && new File(originalUrl).exists()) {
                aliOssUtil.deleteObjectByUrl("student", originalUrl);
//                File path = new File(originalUrl);
//                path.delete();
            }
            student.setPhotoUrl(Url);
        } else {
            student.setPhotoUrl(null);
        }
        studentMapper.update1(student, uuid);
    }
    @Override
    public void uploadStudent(MultipartFile file, StudentVO student) throws IOException {
        String objectName = aliOssUtil.getObjectName(file);
        String url = aliOssUtil.upload(file.getBytes(), "student",objectName);
        //String url = photoConstant.saveFile(file , "student");
        student.setPhotoUrl(url);
        studentMapper.add(student);
    }

    @Override
    public void deleteById(Long id) {
        String originalUrl = studentMapper.getPhotoUrl(id);
        aliOssUtil.deleteObjectByUrl("student", originalUrl);
        studentMapper.deleteById(id);

    }

    @Override
    public void uploadStudent1(MultipartFile file, StudentVO student,String uuid) throws IOException {
        if (file != null){
            String objectName = aliOssUtil.getObjectName(file);
            String url = aliOssUtil.upload(file.getBytes(), "student",objectName);
            //String url = photoConstant.saveFile(file , "student");
            student.setPhotoUrl(url);
        }

        studentMapper.addPre(student,uuid);
    }




    @Override
    public void deleteStudentsByIds(List<Long> ids) {
        for (Long id : ids) {
            String originalUrl = studentMapper.getPhotoUrl(id);
            aliOssUtil.deleteObjectByUrl("student", originalUrl);
        }
        studentMapper.deleteBatchIds(ids);
    }

   /* @Override
    public void updateTeacher(StudentVO studentVO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentVO,student);
        *//*studentMapper.update(student);*//*
    }*/




}
