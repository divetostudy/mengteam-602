package com.web.mapper;

import com.web.pojo.DO.ImgProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-15
 */
public interface ImgProjectMapper extends BaseMapper<ImgProject> {

    void add(Integer id, String url);

    Integer[] getId(Integer id);

    String getUrl(Integer integer);

    void update(Integer id, String url);

    void delete(Integer id);

    List<String> selectByProjectId(Integer id);

    void deleteByUrl(String s);
}
