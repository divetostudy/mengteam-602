package com.web.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.entity.Activities;
import com.web.mapper.ActivitiesMapper;
import com.web.service.IActivitiesService;
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
public class ActivitiesServiceImpl extends ServiceImpl<ActivitiesMapper, Activities> implements IActivitiesService {

    @Autowired
    private ActivitiesMapper activitiesMapper;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public void removeByUrl(String url) {
        aliOssUtil.deleteObjectByUrl("activities", url);
        activitiesMapper.delete(new QueryWrapper<Activities>().eq("photo_url",url));
    }

    @Override
    public void add(MultipartFile image) throws IOException {
        String imageName = aliOssUtil.getObjectName(image);
        String url = aliOssUtil.upload(image.getBytes(), "activities",imageName);
        activitiesMapper.insert(new Activities().setPhotoUrl(url));
    }
}
