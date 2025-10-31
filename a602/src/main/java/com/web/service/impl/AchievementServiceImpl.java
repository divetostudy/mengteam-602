package com.web.service.impl;

import com.web.constant.PhotoConstant;
import com.web.mapper.ImgAchievementMapper;
import com.web.pojo.DO.Achievement;
import com.web.mapper.AchievementMapper;
import com.web.pojo.DO.ImgAchievement;
import com.web.pojo.DTO.AchievementDTO;
import com.web.pojo.VO.AchievementVO;
import com.web.pojo.VO.SelectAchievementVO;
import com.web.service.IAchievementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.util.AliOssUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 成果 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-13
 */
@Service
public class AchievementServiceImpl extends ServiceImpl<AchievementMapper, Achievement> implements IAchievementService {

    @Autowired
    private AchievementMapper achievementMapper;
    @Autowired
    private ImgAchievementMapper imgAchievementMapper;
    @Autowired
    private PhotoConstant photoConstant;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public List<SelectAchievementVO> select() {
        List<Achievement> achievementList = achievementMapper.selectList(null);//获取所有成果
        List<SelectAchievementVO> achievementVOS = new ArrayList<>();
        for (Achievement achievement : achievementList){//将成果分别复制到VOS类中
            SelectAchievementVO achievementVO = new SelectAchievementVO();
            BeanUtils.copyProperties(achievement , achievementVO);//复制类
            List<String> urls = imgAchievementMapper.getUrl(achievement.getId());
            if (urls != null){
                //                    // 读取图片文件的所有字节
                //                    try {
                //                        byte[] imageData = Files.readAllBytes(Path.of(url));
                //                        // 将图片数据设置到StudentVO中
                //                        achievementVO.setPhotos(url , imageData);
                //                    } catch (IOException e) {
                //                        throw new RuntimeException(e);
                //                    }
                urls.forEach(achievementVO::setPhotos);//将配图url添加到vo类
            }
            achievementVOS.add(achievementVO);
        }
        return achievementVOS;

    }

    @Override
    public void add(MultipartFile[] files, AchievementVO achievementVO) throws IOException {
        Achievement achievement = new Achievement();
        ImgAchievement imgAchievement = new ImgAchievement();
        BeanUtils.copyProperties(achievementVO , achievement);
        achievementMapper.add(achievement);
        Integer id = achievement.getId();
        if (files != null){
            for (int i = 0; i < files.length; i++) {
//                String url = photoConstant.saveFile(files[0], "achievement");
                String objectName = aliOssUtil.getObjectName(files[i]);
                String url = aliOssUtil.upload(files[i].getBytes(), "achievement",objectName);
                imgAchievementMapper.add(id , url);
            }
            
        }
    }

    @Override
    public void update(AchievementDTO achievementDTO, MultipartFile[] files , String[] deleteUrl, ImgAchievement[] imgAchievements) throws IOException {
        Achievement achievement = new Achievement();
        BeanUtils.copyProperties(achievementDTO , achievement);
        achievementMapper.update(achievement);
        //配图操作

        //删除需要删除的配图
        if (deleteUrl != null && deleteUrl.length>0) {//判断是否有图片需要删除
            for (int i = 0; i < deleteUrl.length; i++) {
                imgAchievementMapper.deleteByUrl(deleteUrl[i]);
                aliOssUtil.deleteObjectByUrl("achievement", deleteUrl[i]);
            }
        }
        if (files != null && files.length > 0) {//判断是否有上传图片
            //判断原来配图是否为空
            if (imgAchievements != null && imgAchievements.length > 0) {
                ImgAchievement[] imgAchievements1 = new ImgAchievement[imgAchievements.length + files.length];//创建新数组保证数组能容纳所有配图
                // 初始化新增部分
                for (int j = imgAchievements.length; j < imgAchievements1.length; j++) {
                    imgAchievements1[j] = new ImgAchievement();
                }
                System.arraycopy(imgAchievements, 0, imgAchievements1, 0, imgAchievements.length);//复制原数组数据
                InsertImgAchievement(files, achievement, imgAchievements1);//重新录入配图信息至数据库
            }else{
                ImgAchievement[] imgAchievements1 = new ImgAchievement[files.length];
                // 初始化整个数组
                for (int j = 0; j < imgAchievements1.length; j++) {
                    imgAchievements1[j] = new ImgAchievement();
                }
                InsertImgAchievement(files, achievement, imgAchievements1);//重新录入配图信息至数据库
            }



        }
    }

    @Override
    public void delete(Integer id) {
        Integer[] imgId = imgAchievementMapper.getId(id);
        if (imgId != null){
            for (int i = 0; i < imgId.length; i++) {
                String url = imgAchievementMapper.getOriginalUrl(imgId[i]);
                if (url != null) {
                    File path = new File(url);
                    path.delete();
                }
            }
            imgAchievementMapper.delete(id);
        }

        achievementMapper.delete(id);
    }
    private void InsertImgAchievement(MultipartFile[] files, Achievement achievement, ImgAchievement[] imgAchievements) throws IOException {
        for (int i = 0; i < imgAchievements.length; i++) {
            imgAchievements[i].setAchievementId(achievement.getId());
            if (imgAchievements[i].getUrl() == null) {//判断图片是否为空
                String objectName = aliOssUtil.getObjectName(files[i]);
                String url = aliOssUtil.upload(files[i].getBytes(), "achievement", objectName);
                imgAchievements[i].setUrl(url);
            }else
                continue;//若已有url，则跳过
            imgAchievementMapper.insert(imgAchievements[i]);
        }
    }
}
