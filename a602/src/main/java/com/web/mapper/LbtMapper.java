package com.web.mapper;

import com.web.pojo.DO.Lbt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mai
 * @since 2024-06-02
 */
@Mapper
public interface LbtMapper extends BaseMapper<Lbt> {
    void addHomeLbt(String url);

    List<String> getHomeLbt();

    void deleteHomeLbt(String url);

    void addCGLbt(String url);

    List<String> getCGLbt();
/**
 *
 */
}
