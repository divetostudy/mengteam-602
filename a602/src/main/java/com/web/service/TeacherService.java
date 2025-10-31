package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.DO.Teacher;
import com.web.pojo.DTO.TeacherDTO;
import com.web.pojo.VO.SelectTeacherVO;
import com.web.pojo.VO.TeacherVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherService extends IService<Teacher> {

    /**
     * 上传老师
     * @param file
     * @param teacher
     */
    void upload(MultipartFile file, TeacherVO teacher) throws IOException;

    void deleteTeachersByIds(List<Long> ids);

    List<SelectTeacherVO> getTeachers();

    void updateTeacher(MultipartFile file, TeacherDTO teacher) throws IOException;

    void delete(Long id);


}
