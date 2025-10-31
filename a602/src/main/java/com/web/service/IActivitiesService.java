package com.web.service;

import com.web.entity.Activities;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mai
 * @since 2024-09-26
 */
public interface IActivitiesService extends IService<Activities> {

    void removeByUrl(String url);

    void add(MultipartFile image) throws IOException;
}
