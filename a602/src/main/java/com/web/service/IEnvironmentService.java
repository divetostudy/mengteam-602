package com.web.service;

import com.web.entity.Environment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mai
 * @since 2024-09-26
 */
public interface IEnvironmentService extends IService<Environment> {

    void add(MultipartFile image) throws IOException;

    List<String> get();

    void removeByUrl(String url);
}
