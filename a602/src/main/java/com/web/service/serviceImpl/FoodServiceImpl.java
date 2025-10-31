package com.web.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.entity.Activities;
import com.web.entity.Food;
import com.web.mapper.FoodMapper;
import com.web.service.IFoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.util.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mai
 * @since 2024-09-26
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements IFoodService {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private FoodMapper foodMapper;

    @Override
    public void add(MultipartFile image) throws IOException {
        String imageName = aliOssUtil.getObjectName(image);
        String url = aliOssUtil.upload(image.getBytes(), "food",imageName);
        foodMapper.insert(new Food().setPhotoUrl(url));
    }

    @Override
    public void removeByUrl(String url) {
        aliOssUtil.deleteObjectByUrl("food", url);
        foodMapper.delete(new QueryWrapper<Food>().eq("photo_url", url));
    }
}
