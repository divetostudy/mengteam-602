package com.web.service.impl;

import com.web.mapper.UserMapper;
import com.web.pojo.DO.User;
import com.web.pojo.VO.UserLoginVO;
import com.web.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginVO userLoginVO) {
        User user = new User();
        BeanUtils.copyProperties(userLoginVO, user);
        User u = userMapper.login(user);
        return u;
    }
}
