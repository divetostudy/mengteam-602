package com.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.pojo.DO.Student;
import com.web.pojo.DTO.StudentDTO;
import com.web.pojo.VO.SelectStudentVO;
import com.web.pojo.VO.StudentVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService extends IService<Student> {



    void deleteStudentsByIds(List<Long> ids);


    /*void updateTeacher(StudentVO teacherDTO);*/

    List<SelectStudentVO> getStudent();

    List<StudentVO> getStudentByName(String name);

    void update(MultipartFile file, StudentDTO student) throws IOException;

    void uploadStudent(MultipartFile file, StudentVO student) throws IOException;

    void deleteById(Long id);

    void uploadStudent1(MultipartFile file, StudentVO student,String uuid) throws IOException;

    void update1(MultipartFile file, StudentDTO studentDTO,String uuid) throws IOException;
}
