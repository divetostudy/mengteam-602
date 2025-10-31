package com.web.mapper;

import com.web.pojo.DO.NewsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 新闻分类 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
public interface NewsCategoryMapper extends BaseMapper<NewsCategory> {

    List<NewsCategory> List();
}
