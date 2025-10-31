package com.web;

import com.web.mapper.TeacherMapper;
import com.web.properties.JwtProperties;
import com.web.service.TeacherService;
import com.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = A602Application.class)
class A602ApplicationTests {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Test
    void contextLoads() {
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),"eyJhbGciOiJIUzI1NiJ9.eyJzdHVkZW50IjoiWlNBNjAyIiwiZXhwIjoxNzI4MjA3MDgzfQ.zSJAJJO1DScuvHOH7ujaiKWLUGSNdskWEv5KoFQHQJg");

        System.out.println(claims);
        String uuid = claims.get("exp").toString();
        System.out.println(uuid);

    }

}
