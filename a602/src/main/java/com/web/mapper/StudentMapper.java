package com.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.pojo.DO.Student;
import com.web.pojo.DTO.StudentDTO;
import com.web.pojo.VO.SelectStudentVO;
import com.web.pojo.VO.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {


    List<Student> findAllWithPage();

    void update(StudentDTO student);


    String getPhotoUrl(Long id);

    void add(StudentVO student);

    List<Student> selectByName(String name);

    List<Student> getById(Integer id);

    void addPre(@Param("student") StudentVO student,@Param("uuid") String uuid);

    List<SelectStudentVO> getStudent(String uuid);

    void update1(@Param("student") StudentDTO student,@Param("uuid") String uuid);

    List<Student> selectPre();

    Student selectOnePre(Long id);

    void deleteOnePre(Long id);

    String getPrePhotoUrl(Long id);
}
