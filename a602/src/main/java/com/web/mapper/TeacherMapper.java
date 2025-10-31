package com.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.pojo.DO.Teacher;
import com.web.pojo.DTO.TeacherDTO;
import com.web.pojo.VO.TeacherVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

//    void deleteByIds(List<Long> ids);



    List<Teacher> findAllWithPage();

    void update(TeacherDTO teacher);

    String getPhotoUrl(Long id);

    void addTeacher(TeacherVO teacher);


    List<Teacher> getTeacher();

    void delete(Long id);
}
