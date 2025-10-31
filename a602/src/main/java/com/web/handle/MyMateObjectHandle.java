package com.web.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共填充类，主要负责获取和填充时间
 **/
@Slf4j      //打印日志
@Component  //托管给spring boot
public class MyMateObjectHandle implements MetaObjectHandler {

    //插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("start insert fill......");
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("modifiedTime", new Date(), metaObject);
        // this.setFieldValByName();
    }

    //更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("start update fill......");
        this.setFieldValByName("modifiedTime", new Date(), metaObject);
    }

}
