package com.web.service;

import com.web.pojo.DO.NewsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 新闻分类 服务类
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
public interface INewsCategoryService extends IService<NewsCategory> {

    List<NewsCategory> find();
}
