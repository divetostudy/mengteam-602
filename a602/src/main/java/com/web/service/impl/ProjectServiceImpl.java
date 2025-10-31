package com.web.service.impl;

import com.web.constant.PhotoConstant;
import com.web.mapper.ImgProjectMapper;
import com.web.pojo.DO.*;
import com.web.mapper.ProjectMapper;
import com.web.pojo.DTO.ProjectDTO;
import com.web.pojo.VO.ProjectVO;
import com.web.pojo.VO.SelectProjectVO;
import com.web.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.util.AliOssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-15
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ImgProjectMapper imgProjectMapper;
    @Autowired
    private PhotoConstant photoConstant;
    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public List<SelectProjectVO> get() {
        List<Project> projects = projectMapper.selectList(null);
        List<SelectProjectVO> projectVOList  = new ArrayList<>();
        for (Project project : projects){
            SelectProjectVO projectVO = new SelectProjectVO();
            BeanUtils.copyProperties(project , projectVO);
            String imgUrl = project.getUrl();
            projectVO.setPhoto(imgUrl);
//            // 使用Optional，如果imageUrl不为空，则尝试读取图片数据
//            Optional.ofNullable(imgUrl)
//                    .ifPresent(url -> {
//                        try {
//                            // 读取图片文件的所有字节
//                            byte[] imageData = Files.readAllBytes(Path.of(url));
//                            // 将图片数据设置到StudentVO中
//                            projectVO.setPhoto(imgUrl , imageData);
//                        } catch (IOException e) {
//                            // 如果发生错误，抛出RuntimeException
//                            throw new RuntimeException(e);
//                        }
//                    });
//
//            // 将学生视图对象添加到列表中
            projectVOList.add(projectVO);
        }
        return projectVOList;
        }

    @Override
    public void add(MultipartFile[] files, MultipartFile image, ProjectVO projectVO) throws IOException {
        Project project = new Project();
        BeanUtils.copyProperties(projectVO , project);
        if (image != null){
//            String url = photoConstant.saveFile(image , "project");
            String objectName = aliOssUtil.getObjectName(image);
            String url = aliOssUtil.upload(image.getBytes(), "project",objectName);
            project.setUrl(url);
        }
        projectMapper.add(project);
        Integer id = project.getId();
        if (files != null){
            for (int i = 0; i < files.length; i++) {
//                String url = photoConstant.saveFile(files[i] , "news");
                String objectName = aliOssUtil.getObjectName(files[i]);
                String url = aliOssUtil.upload(files[i].getBytes(), "project",objectName);
                //将本地url和新闻id存入img-project表
                imgProjectMapper.add(id , url);
            }
        }
    }


    @Override
    public void update(MultipartFile[] files, Img img, ProjectDTO projectDTO, ImgProject[] imgProjects, String[] deleteUrl) throws IOException{
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO , project);
        //封面操作
        if (img.getFile() != null || img.getUrl() != null) {//判断是否有图片
            //获取原图路径
            String originalUrl = projectMapper.getUrl(project.getId());
            if (!originalUrl.isEmpty()) {//判断原来是否有图片
                //有图片
                if (img.getUrl() != null && img.getFile() == null) {
                    project.setUrl(img.getUrl());//如果图片没有修改，则保持不变
                } else if (img.getUrl() == null && img.getFile() != null) {//如果图片改变
                    aliOssUtil.deleteObjectByUrl("project", originalUrl);//删除
                    String objectName = aliOssUtil.getObjectName(img.getFile());
                    String url = aliOssUtil.upload(img.getFile().getBytes(), "project", objectName);
                    project.setUrl(url);
                } else {
                    aliOssUtil.deleteObjectByUrl("project", originalUrl);
                    project.setUrl(null);
                }
            } else {
                //无图片
                if (img.getFile() != null) {//判断是否有新增图片
                    String objectName = aliOssUtil.getObjectName(img.getFile());
                    String url = aliOssUtil.upload(img.getFile().getBytes(), "project", objectName);
                    project.setUrl(url);
                }
            }
        }else {
            project.setUrl(null);
        }

        projectMapper.update(project);

        //配图操作

        //删除需要删除的配图
        if (deleteUrl != null && deleteUrl.length>0) {//判断是否有图片需要删除
            for (int i = 0; i < deleteUrl.length; i++) {
                imgProjectMapper.deleteByUrl(deleteUrl[i]);
                aliOssUtil.deleteObjectByUrl("project", deleteUrl[i]);
            }
        }
        if (files != null && files.length > 0) {//判断是否有上传图片
            //判断原来配图是否为空
            if (imgProjects != null && imgProjects.length > 0) {
                ImgProject[] imgProjects1 = new ImgProject[imgProjects.length + files.length];//创建新数组保证数组能容纳所有配图
                // 初始化新增部分
                for (int j = imgProjects.length; j < imgProjects1.length; j++) {
                    imgProjects1[j] = new ImgProject();
                }
                System.arraycopy(imgProjects, 0, imgProjects1, 0, imgProjects.length);//复制原数组数据
                InsertImgProjects(files, project, imgProjects1);//重新录入配图信息至数据库
            }else{
                ImgProject[] imgProjects1 = new ImgProject[files.length];
                // 初始化整个数组
                for (int j = 0; j < imgProjects1.length; j++) {
                    imgProjects1[j] = new ImgProject();
                }
                InsertImgProjects(files, project, imgProjects1);//重新录入配图信息至数据库
            }



        }
    }

    @Override
    public void delete(Integer id) {
        String url = projectMapper.getUrl(id);
        if (url != null){
//            File path = new File(url);
//            path.delete();
            aliOssUtil.deleteObjectByUrl("project", url);
        }
        Integer[] imgId = imgProjectMapper.getId(id);
        if (imgId != null){
            for (int i = 0; i < imgId.length; i++) {
                String originalUrl = imgProjectMapper.getUrl(imgId[i]);
                if (originalUrl != null) {
//                    File path = new File(originalUrl);
//                    path.delete();
                    aliOssUtil.deleteObjectByUrl("project", originalUrl);
                }
            }
            imgProjectMapper.delete(id);
        }
        projectMapper.delete(id);
    }

    @Override
    public List<SelectProjectVO> select(Integer id) {
        List<Project> projects = projectMapper.getById(id);
        List<SelectProjectVO> projectVOList = new ArrayList<>();
        for (Project project : projects){
            SelectProjectVO projectVO = new SelectProjectVO();
            BeanUtils.copyProperties(project , projectVO);
            if (project.getUrl() != null){
//                try {
//                    byte[] imgData = Files.readAllBytes(Path.of(project.getUrl()));
//                    projectVO.setPhoto(project.getUrl() , imgData);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

                projectVO.setPhoto(project.getUrl());
            }
            List<String> photoList = imgProjectMapper.selectByProjectId(project.getId());
            if (photoList != null){
                //                    try {
                //                        byte[] imgData = Files.readAllBytes(Path.of(url));
                //                        projectVO.setPhotos(url , imgData);
                //                    } catch (IOException e) {
                //                        throw new RuntimeException(e);
                //                    }
                photoList.forEach(projectVO::setPhotos);//设置配图url到vo类中
            }
            projectVOList.add(projectVO);
        }
        return projectVOList;
    }

    private void InsertImgProjects(MultipartFile[] files, Project project, ImgProject[] imgProjects) throws IOException {
        for (int i = 0; i < imgProjects.length; i++) {
            imgProjects[i].setProjectId(project.getId());
            if (imgProjects[i].getUrl() == null) {//判断图片是否为空
                String objectName = aliOssUtil.getObjectName(files[i]);
                String url = aliOssUtil.upload(files[i].getBytes(), "project", objectName);
                imgProjects[i].setUrl(url);
            }else
                continue;
            imgProjectMapper.insert(imgProjects[i]);
        }
    }

}





