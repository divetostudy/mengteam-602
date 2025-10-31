package com.web.service.impl;

import com.web.pojo.DO.Lbt;
import com.web.mapper.LbtMapper;
import com.web.service.ILbtService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.util.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mai
 * @since 2024-06-02
 */
@Service
public class LbtServiceImpl extends ServiceImpl<LbtMapper, Lbt> implements ILbtService {
    @Autowired
    private LbtMapper lbtMapper;

    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public void addHomeLbt(MultipartFile image) throws IOException {
        String imageName = aliOssUtil.getObjectName(image);
        String url = aliOssUtil.upload(image.getBytes(), "lbt",imageName);
        lbtMapper.addHomeLbt(url);
    }

    @Override
    public List<String> getHomeLbt() {
        return lbtMapper.getHomeLbt();
    }

    @Override
    public void deleteHomeLbt( String url) {
        aliOssUtil.deleteObjectByUrl("lbt", url);
        lbtMapper.deleteHomeLbt(url);
    }

    @Override
    public void addCGLbt(MultipartFile image) throws IOException {
        String imageName = aliOssUtil.getObjectName(image);
        String url = aliOssUtil.upload(image.getBytes(), "lbt",imageName);
        lbtMapper.addCGLbt(url);
    }

    @Override
    public List<String> getCGLbt() {
        return lbtMapper.getCGLbt();
    }
}
