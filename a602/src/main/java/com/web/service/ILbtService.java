package com.web.service;

import com.web.pojo.DO.Lbt;
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
 * @since 2024-06-02
 */
public interface ILbtService extends IService<Lbt> {

    void addHomeLbt(MultipartFile image) throws IOException;

    List<String> getHomeLbt( );

    void deleteHomeLbt( String url);

    void addCGLbt(MultipartFile image) throws IOException;

    List<String> getCGLbt();
}
