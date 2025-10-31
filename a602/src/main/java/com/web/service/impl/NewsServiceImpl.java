package com.web.service.impl;

import com.web.constant.PhotoConstant;
import com.web.mapper.ImgNewsMapper;
import com.web.pojo.DO.Img;
import com.web.pojo.DO.ImgNews;
import com.web.pojo.DO.News;
import com.web.mapper.NewsMapper;
import com.web.pojo.DTO.NewsDTO;
import com.web.pojo.VO.NewsVO;
import com.web.pojo.VO.SelectNewsVO;
import com.web.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.util.AliOssUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 新闻信息 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-11
 */
@Service
@MapperScan("com.web.mapper")
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private ImgNewsMapper imgNewsMapper;
    @Autowired
    private PhotoConstant photoConstant;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Override
    public void add(MultipartFile[] files, NewsVO newsVO, MultipartFile img) throws IOException {
        News news = new News();
        BeanUtils.copyProperties(newsVO , news);
        if (img != null) {
//              String imgUrl = photoConstant.saveFile(img, "news");
//              news.setImg(imgUrl);
            String objectName = aliOssUtil.getObjectName(img);
            String url = aliOssUtil.upload(img.getBytes(), "news",objectName);
            news.setImg(url);
        }
        newsMapper.add(news);
        Integer id = news.getId();
        //把new里的base64转成图片存在本地，保存本地url
        if (files != null){
            for (int i = 0; i < files.length; i++) {
//                String url = photoConstant.saveFile(files[i] , "news");

                String objectName = aliOssUtil.getObjectName(files[i]);
                String url = aliOssUtil.upload(files[i].getBytes(), "news",objectName);
                imgNewsMapper.add(id , url);//将url和新闻id存入img-news表
            }
        }
    }

    @Override
    public List<SelectNewsVO> getnews() {
        List<News> news = newsMapper.selectList(null);
        List<SelectNewsVO> newsVOS = new ArrayList<>();
        for (News news1 : news ){
            SelectNewsVO newsVO = new SelectNewsVO();
            BeanUtils.copyProperties(news1, newsVO);
            String imgUrl = news1.getImg();
            newsVO.setPhoto(imgUrl);
//            // 使用Optional，如果imageUrl不为空，则尝试读取图片数据
//            Optional.ofNullable(imgUrl)
//                    .ifPresent(url -> {
//                        try {
//                            // 读取图片文件的所有字节
//                            byte[] imageData = Files.readAllBytes(Path.of(url));
//                            // 将图片数据设置到StudentVO中
//                            newsVO.setPhoto(imgUrl , imageData);
//                        } catch (IOException e) {
//                            // 如果发生错误，抛出RuntimeException
//                            throw new RuntimeException(e);
//                        }
//                    });

            // 将视图对象添加到列表中
            newsVOS.add(newsVO);
        }
        return newsVOS;
    }

    @Override
    public List<SelectNewsVO> getnews(Integer id) {
        List<News> newsList = newsMapper.selectByTitle(id);
        List<SelectNewsVO> newsVOS = new ArrayList<>();
        for (News news : newsList){
            SelectNewsVO newsVO = new SelectNewsVO();
            BeanUtils.copyProperties(news , newsVO);
            if (news.getImg() != null){
//                try {
//                    byte[] imgData = Files.readAllBytes(Path.of(news.getImg()));
//                    newsVO.setPhoto(news.getImg() , imgData);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            newsVO.setPhoto(news.getImg());//将路径url添加到VO类
            }
            List<String> photoList = imgNewsMapper.selectByNewsId(news.getId());
            if (photoList != null){
                //                    try {
                //                        byte[] imgData = Files.readAllBytes(Path.of(s));
                //                        newsVO.setPhotos(s , imgData);
                //                    } catch (IOException e) {
                //                        throw new RuntimeException(e);
                //                    }
                photoList.forEach(newsVO::setPhotos);//将配图url添加到vo类
            }
            newsVOS.add(newsVO);
        }
        return newsVOS;
    }

    @Override
    public void update(MultipartFile[] files, NewsDTO newsDTO, Img img, ImgNews[] imgNews, String[] deleteUrl) throws IOException {
        News news = new News();
        BeanUtils.copyProperties(newsDTO, news);
        //封面操作
        if (img.getFile() != null || img.getUrl() != null) {//判断是否有图片
            //获取原图路径
            String originalUrl = newsMapper.getUrl(news.getId());
            if (!originalUrl.isEmpty()) {//判断原来是否有图片
                //有图片
                if (img.getUrl() != null && img.getFile() == null) {
                    news.setImg(img.getUrl());//如果图片没有修改，则保持不变
                } else if (img.getUrl() == null && img.getFile() != null) {//如果图片改变
                    aliOssUtil.deleteObjectByUrl("news", originalUrl);//删除
                    String objectName = aliOssUtil.getObjectName(img.getFile());
                    String url = aliOssUtil.upload(img.getFile().getBytes(), "news", objectName);
                    news.setImg(url);
                } else {
                    aliOssUtil.deleteObjectByUrl("news", originalUrl);
                    news.setImg(null);
                }
            } else {
                //无图片
                if (img.getFile() != null) {//判断是否有新增图片
                    String objectName = aliOssUtil.getObjectName(img.getFile());
                    String url = aliOssUtil.upload(img.getFile().getBytes(), "news", objectName);
                    news.setImg(url);
                }
            }
        }else {
            news.setImg(null);
        }

            newsMapper.update(news);
            //配图操作

            //删除需要删除的配图
        if (deleteUrl != null && deleteUrl.length>0) {//判断是否有图片需要删除
            for (int i = 0; i < deleteUrl.length; i++) {
                imgNewsMapper.deleteByUrl(deleteUrl[i]);
                aliOssUtil.deleteObjectByUrl("news", deleteUrl[i]);
            }
        }
        if (files != null && files.length > 0) {//判断是否有上传图片
            //判断原来配图是否为空
            if (imgNews != null && imgNews.length > 0) {
                ImgNews[] imgNews1 = new ImgNews[imgNews.length + files.length];//创建新数组保证数组能容纳所有配图
                // 初始化新增部分
                for (int j = imgNews.length; j < imgNews1.length; j++) {
                    imgNews1[j] = new ImgNews();
                }
                System.arraycopy(imgNews, 0, imgNews1, 0, imgNews.length);//复制原数组数据
                InsertImgNews(files, news, imgNews1);//重新录入配图信息至数据库
            }else{
                ImgNews[] imgNews1 = new ImgNews[files.length];
                // 初始化整个数组
                for (int j = 0; j < imgNews1.length; j++) {
                    imgNews1[j] = new ImgNews();
                }
                InsertImgNews(files, news, imgNews1);//重新录入配图信息至数据库
            }



        }

    }

    private void InsertImgNews(MultipartFile[] files, News news, ImgNews[] imgNews1) throws IOException {
        for (int i = 0; i < imgNews1.length; i++) {
            imgNews1[i].setNewsId(news.getId());
            if (imgNews1[i].getUrl() == null) {//判断图片是否为空
                String objectName = aliOssUtil.getObjectName(files[i]);
                String url = aliOssUtil.upload(files[i].getBytes(), "news", objectName);
                imgNews1[i].setUrl(url);
            }else
                continue;//若已有url，则跳过
            imgNewsMapper.insert(imgNews1[i]);
        }
    }

    @Override
    public void delete(Integer id) {
        String url = newsMapper.getUrl(id);
        if (url != null){
//            File path = new File(url);
//            path.delete();
            aliOssUtil.deleteObjectByUrl("news", url);
        }
        Integer[] imgId = imgNewsMapper.getByNewsId(id);
        if (imgId != null){
            for (int i = 0; i < imgId.length; i++) {
                String originalUrl = imgNewsMapper.getUrl(imgId[i]);
                if (originalUrl != null) {
//                    File path = new File(originalUrl);
//                    path.delete();
                    aliOssUtil.deleteObjectByUrl("news", originalUrl);
                }
            }
            imgNewsMapper.delete(id);
        }
        newsMapper.delete(id);
    }
}
