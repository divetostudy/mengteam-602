package com.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.web.constant.PhotoConstant;
import com.web.mapper.TeacherMapper;
import com.web.pojo.DO.Teacher;
import com.web.pojo.DTO.TeacherDTO;
import com.web.pojo.VO.SelectTeacherVO;
import com.web.pojo.VO.TeacherVO;
import com.web.service.TeacherService;
import com.web.common.util.FileNameUtils;
import com.web.util.AliOssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private PhotoConstant photoConstant;

    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * 获取老师信息
     *
     * @return
     */
    @Override
    public List<SelectTeacherVO> getTeachers() {
        // 从数据库中获取教师列表
        List<Teacher> teachers = teacherMapper.getTeacher();
        // 创建一个用于存储转换后教师信息的列表
        List<SelectTeacherVO> teacherVOList = new ArrayList<>();

        // 遍历教师列表
        for (Teacher teacher : teachers) {
            // 为每个教师创建一个TeacherVO对象
            SelectTeacherVO teacherVO = new SelectTeacherVO();

            // 使用BeanUtils.copyProperties方法将Teacher对象的属性复制到TeacherVO对象中
            BeanUtils.copyProperties(teacher, teacherVO);

//            // 获取教师对象的photoUrl属性
//            String imageUrl = teacher.getPhotoUrl();
//            // 使用Optional.ofNullable判断imageUrl是否为空
//            Optional.ofNullable(imageUrl)
//                    .ifPresent(url -> {
//                        try {
//                            // 读取图片文件的内容
//                            byte[] imageData = Files.readAllBytes(Path.of(url));
//                            // 将图片内容设置为TeacherVO对象的photo属性
//                            teacherVO.setPhoto(imageData);
//                        } catch (IOException e) {
//                            // 抛出一个运行时异常
//                            throw new RuntimeException(e);
//                        }
//                    });

            // 将转换后的TeacherVO对象添加到teacherVOList列表中
            teacherVOList.add(teacherVO);
        }
        System.out.println(111);
        // 返回转换后的教师信息列表
        return teacherVOList;
    }

    @Override
    public void updateTeacher(MultipartFile file, TeacherDTO teacher) throws IOException {
        if(file != null)
        {
            if (file != null) {
                String objectName = aliOssUtil.getObjectName(file);
                String Url = aliOssUtil.upload(file.getBytes(), "teacher",objectName);
                //String Url = photoConstant.saveFile(file , "teacher");
                String originalUrl = teacherMapper.getPhotoUrl(teacher.getId());

                if (originalUrl != null) {//删除原文件
                    aliOssUtil.deleteObjectByUrl("teacher", originalUrl);
                }
                teacher.setPhotoUrl(Url);
            }else {
                teacher.setPhotoUrl(null);
            }

            teacherMapper.update(teacher);
        }
        // 更新教师信息

        teacherMapper.update(teacher);
    }

    @Override
    public void delete(Long id) {
        String url = teacherMapper.getPhotoUrl(id);
        if (url != null){
            aliOssUtil.deleteObjectByUrl("teacher", url);
        }
        teacherMapper.delete(id);
    }


    /**
     * 上传老师
     * @param file
     * @param teacher
     */
    @Override
    public void upload(MultipartFile file, TeacherVO teacher) throws IOException {
        String objectName = aliOssUtil.getObjectName(file);
        String url = aliOssUtil.upload(file.getBytes(), "teacher",objectName);
        //String url = photoConstant.saveFile(file , "teacher");
        teacher.setPhotoUrl(url);
        teacherMapper.addTeacher(teacher);

    }


    @Override
    public void deleteTeachersByIds(List<Long> ids) {

        teacherMapper.deleteBatchIds(ids);
    }






}
