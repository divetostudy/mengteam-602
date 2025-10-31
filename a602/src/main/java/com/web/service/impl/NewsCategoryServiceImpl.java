package com.web.service.impl;

import com.web.pojo.DO.NewsCategory;
import com.web.mapper.NewsCategoryMapper;
import com.web.service.INewsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 新闻分类 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
@Service
public class NewsCategoryServiceImpl extends ServiceImpl<NewsCategoryMapper, NewsCategory> implements INewsCategoryService {
    @Autowired
    private NewsCategoryMapper newsCategoryMapper;
    @Override
    public List<NewsCategory> find() {
        List<NewsCategory> newsCategories = newsCategoryMapper.List();
        return newsCategories;
    }
}
