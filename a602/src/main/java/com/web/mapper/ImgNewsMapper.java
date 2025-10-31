package com.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.pojo.DO.ImgNews;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 新闻配图 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
@Mapper
public interface ImgNewsMapper extends BaseMapper<ImgNews> {

    void add(Integer id, String url);

    List<String> selectByNewsId(Integer id);

    void update(Integer id, String url);

    Integer[] getByNewsId(Integer id);

    String getUrl(Integer integer);

    void delete(Integer id);

    void deleteByUrl(String s);
}
