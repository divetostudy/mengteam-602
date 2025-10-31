package com.web.service.serviceImpl;

import com.web.entity.Environment;
import com.web.mapper.EnvironmentMapper;
import com.web.service.IEnvironmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.util.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mai
 * @since 2024-09-26
 */
@Service
public class EnvironmentServiceImpl extends ServiceImpl<EnvironmentMapper, Environment> implements IEnvironmentService {
    @Autowired
    private EnvironmentMapper environmentMapper;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public void add(MultipartFile image) throws IOException {
        String imageName = aliOssUtil.getObjectName(image);
        String url = aliOssUtil.upload(image.getBytes(), "environment",imageName);
        environmentMapper.add(url);
    }

    @Override
    public List<String> get() {
        return environmentMapper.get();
    }

    @Override
    public void removeByUrl(String url) {
        aliOssUtil.deleteObjectByUrl("environment", url);
        environmentMapper.deletePhoto(url);

    }
}
