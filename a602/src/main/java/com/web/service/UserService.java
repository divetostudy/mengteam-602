package com.web.service;


import com.web.pojo.DO.User;
import com.web.pojo.VO.UserLoginVO;

public interface UserService  {
    User login(UserLoginVO userLoginVO);
}
