package com.web.mapper;

import com.web.pojo.DO.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 新闻信息 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {

    void delete(Integer id) ;

    void add(News news);

    List<News> selectByTitle(Integer id);

    void update(News news);

    String getUrl(Integer id);
}
