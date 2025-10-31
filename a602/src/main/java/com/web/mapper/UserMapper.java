package com.web.mapper;

import com.web.pojo.DO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    User login(User user);


    String getPassword(String username);
}
